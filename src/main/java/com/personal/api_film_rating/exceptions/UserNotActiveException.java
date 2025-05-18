package com.personal.api_film_rating.exceptions;

public class UserNotActiveException extends RuntimeException {
  public UserNotActiveException(String message) {
    super(message);
  }
}
