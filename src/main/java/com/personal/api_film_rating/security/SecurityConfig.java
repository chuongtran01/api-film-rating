package com.personal.api_film_rating.security;

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

    public SecurityConfig(UserRepository userRepository, JwtFilter jwtFilter, CorsConfig corsConfig) {
        this.userRepository = userRepository;
        this.jwtFilter = jwtFilter;
        this.corsConfig = corsConfig;
    }

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(c -> c.configurationSource(corsConfig))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/refresh-token", "/error")
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        return http.build();
    }

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

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
