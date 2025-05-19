package com.personal.api_film_rating.service;

import com.personal.api_film_rating.configuration.JwtConfig;
import com.personal.api_film_rating.entity.LoginUser;
import com.personal.api_film_rating.entity.RefreshToken;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.repository.RefreshTokenRepository;
import com.personal.api_film_rating.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final String TOKEN_BLACK_LIST_PREFIX = "TOKEN_BLACK_LIST_%s_%s";
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisService redisService;
    private final ApplicationContext applicationContext;

    public JwtServiceImpl(
            JwtConfig jwtConfig,
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            RedisService redisService,
            ApplicationContext applicationContext) {
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.redisService = redisService;
        this.applicationContext = applicationContext;
    }

    @Override
    public String generateAccessToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        User user = userRepository.findByEmail(email);

        claims.put("role", user.getRole().getName());
        claims.put("id", user.getId());
        claims.put("gender", user.getGender());
        claims.put("dob", user.getDob().toString());
        claims.put("displayName", user.getDisplayName());
        claims.put("jit", generateJitKey());

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiration()))
                .and()
                .signWith(getKey())
                .compact();
    }

    @Override
    @Transactional
    public String generateRefreshToken(String accessToken) {
        // Revoke old refresh token
        LoginUser loginUser = (LoginUser) applicationContext.getBean(UserDetailsService.class)
                .loadUserByUsername(extractEmail(accessToken));

        revokeRefreshToken(loginUser.getUser());

        LocalDateTime expireAt = LocalDateTime.now().plus(Duration.ofMillis(jwtConfig.getRefreshTokenExpiration()));

        String refreshToken = Jwts.builder()
                .claims()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()))
                .and()
                .signWith(getKey())
                .compact();

        refreshTokenRepository.save(new RefreshToken(refreshToken, loginUser.getUser(), expireAt));

        return refreshToken;
    }

    @Override
    public RefreshToken findRefreshTokenByRefreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken);

        if (token == null || token.isRevoked() || isTokenExpired(token.getToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return token;
    }

    @Override
    public RefreshToken revokeRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByUserIdAndRevokedIsFalseAndExpiresAtGreaterThan(user.getId(), LocalDateTime.now());

        if (refreshToken != null) {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;
    }

    @Override
    public void blacklistAccessToken(String accessToken) {
        try {
            Claims claims = extractAllClaims(accessToken);
            String userId = claims.get("id", String.class);
            String jit = claims.get("jit", String.class);

            long millis = extractExpiration(accessToken).getTime() - System.currentTimeMillis();

            if (millis > 0) {
                Duration ttl = Duration.ofMillis(millis);
                String key = String.format(TOKEN_BLACK_LIST_PREFIX, userId, jit);
                redisService.save(key, "true", ttl);
            }
        } catch (ExpiredJwtException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing the token", e);
        }
    }

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenBlacklisted(String accessToken) {
        Claims claims = extractAllClaims(accessToken);
        String userId = claims.get("id", String.class);
        String jit = claims.get("jit", String.class);

        String key = String.format(TOKEN_BLACK_LIST_PREFIX, userId, jit);
        String value = redisService.get(key);

        return !Boolean.TRUE.equals(Boolean.valueOf(value));
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private SecretKey getKey() throws RuntimeException {
        byte[] secretKeyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    private String generateJitKey() {
        byte[] randomBytes = new byte[32]; // 256 bits
        new java.security.SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
