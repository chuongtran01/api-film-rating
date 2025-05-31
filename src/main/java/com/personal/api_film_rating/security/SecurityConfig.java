package com.personal.api_film_rating.security;

import com.personal.api_film_rating.configuration.JwtConfig;
import com.personal.api_film_rating.entity.LoginUser;
import com.personal.api_film_rating.entity.User;
import com.personal.api_film_rating.exceptions.UserNotActiveException;
import com.personal.api_film_rating.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;
    private final JwtFilter jwtFilter;

    private final CorsConfig corsConfig;

    private final JwtConfig jwtConfig;

    public SecurityConfig(UserRepository userRepository, JwtFilter jwtFilter, CorsConfig corsConfig,
            JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.jwtFilter = jwtFilter;
        this.corsConfig = corsConfig;
        this.jwtConfig = jwtConfig;
    }

    /**
     * BCrypt password encoder
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /*
     * Throw 409 but receive 403 Forbidden instead? Why? (Without implementing
     * ControllerAdvice)
     * - When throw an Exception, you go redirected to the default error handler
     * page which is /error and handled by the BasicErrorController
     * your authentication is being cleared from the SecurityContext (Set
     * SecurityContextHolder to anonymous SecurityContext) and you're being
     * redirected to a page under lock and key, hence the 403.
     */

    /**
     * Security filter chain
     * 
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(c -> c.configurationSource(corsConfig))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(jwtConfig.getWhitelist().toArray(String[]::new))
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        return http.build();
    }

    /**
     * User details service
     * 
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User loginUser = userRepository.findByEmail(email);

            if (loginUser == null) {
                throw new UsernameNotFoundException("User not found");
            }

            if (!loginUser.isActive()) {
                throw new UserNotActiveException("User is not active");
            }

            return new LoginUser(loginUser);
        };
    }

    /**
     * Authentication provider
     * 
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());

        return provider;
    }

    /**
     * Authentication manager
     * 
     * @param config
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
