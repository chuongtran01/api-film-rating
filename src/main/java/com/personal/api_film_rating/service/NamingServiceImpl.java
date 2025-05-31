package com.personal.api_film_rating.service;

import org.springframework.stereotype.Service;

@Service
public class NamingServiceImpl implements NamingService {
  @Override
  public String generateDisplayName(String email) {
    return email.split("@")[0];
  }
}