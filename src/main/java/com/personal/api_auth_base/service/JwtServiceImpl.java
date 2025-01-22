package com.personal.api_auth_base.service;

import com.personal.api_auth_base.model.LoginUser;
import com.personal.api_auth_base.model.RefreshToken;
import com.personal.api_auth_base.model.User;
import com.personal.api_auth_base.repository.RefreshTokenRepository;
import com.personal.api_auth_base.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
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
public class JwtServiceImpl implements JwtService {
    private String SECRET_KEY;
    private final Long ACCESS_TOKEN_EXPIRED_TIME = (long) (1000 * 60 * 15);
    private final Long REFRESH_TOKEN_EXPIRED_TIME = (long) (1000 * 60 * 60 * 24 * 15);


    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final RedisService redisService;

    private final ApplicationContext applicationContext;

    public JwtServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, RedisService redisService, ApplicationContext applicationContext) throws RuntimeException {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.redisService = redisService;
        this.applicationContext = applicationContext;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            SECRET_KEY = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to generate key");
        }

    }

    @Override
    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        // Add user's roles
        //  User user = userRepository.findByUsername(username);
        claims.put("roles", "customer");

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME)) // 15 min
                .and()
                .signWith(getKey())
                .compact();
    }

    @Override
    @Transactional
    public String generateRefreshToken(String accessToken) {
        // Revoke old refresh token
        LoginUser loginUser = (LoginUser) applicationContext.getBean(UserDetailsService.class).loadUserByUsername(extractUsername(accessToken));

        revokeRefreshToken(loginUser.getUser());

        LocalDateTime expireAt = LocalDateTime.now().plus(Duration.ofMillis(REFRESH_TOKEN_EXPIRED_TIME));

        String refreshToken = Jwts.builder()
                .claims()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant())) // 15 days
                .and()
                .signWith(getKey())
                .compact();

        refreshTokenRepository.save(new RefreshToken(refreshToken, loginUser.getUser(), expireAt));

        return refreshToken;
    }

    @Override
    public RefreshToken findRefreshTokenByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Override
    public RefreshToken revokeRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserUserIdAndRevokedIsFalseAndExpiresAtGreaterThan(user.getUserId(), LocalDateTime.now());

        if (refreshToken != null) {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;
    }

    @Override
    public void blacklistAccessToken(String accessToken) {
        String tokenHash = hashToken(accessToken);

        Long expirationTime = extractExpiration(accessToken).getTime() - System.currentTimeMillis();

        redisService.save("blacklist", tokenHash, "blacklisted", expirationTime);
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
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenBlacklisted(String accessToken) {
        String tokenHash = hashToken(accessToken); // Hash the token to match Redis storage
        return redisService.get("blacklist", tokenHash) != null;
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() throws RuntimeException {
        byte[] secretKeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretKeyBytes);

    }
}
