package com.personal.api_film_rating.service;

public interface NamingService {
  /**
   * Generates a random display name based on the email address
   * 
   * @param email The email address to base the name on
   * @return A randomly generated display name
   */
  String generateDisplayName(String email);
}