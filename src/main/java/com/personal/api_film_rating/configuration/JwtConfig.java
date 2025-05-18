package com.personal.api_film_rating.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
  private String secret;
  private Long accessTokenExpiration;
  private Long refreshTokenExpiration;
}
