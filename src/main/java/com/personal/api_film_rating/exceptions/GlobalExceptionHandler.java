package com.personal.api_film_rating.exceptions;

// package com.personal.api_auth_base.exceptions;
//
// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.validation.FieldError;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.server.ResponseStatusException;
//
// import java.util.Map;
// import java.util.SortedMap;
// import java.util.TreeMap;
//
// @RestControllerAdvice
// public class GlobalExceptionHandler {
// @ExceptionHandler(MethodArgumentNotValidException.class)
// public ResponseEntity<Map<String, String>>
// handleValidationErrors(MethodArgumentNotValidException ex) {
// SortedMap<String, String> errors = new TreeMap<>();
// ex.getBindingResult().getAllErrors().forEach((error) -> {
// var fieldName = ((FieldError) error).getField();
// var errorMessage = error.getDefaultMessage();
// errors.put(fieldName, errorMessage);
// });
// return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
// }
//
// @ExceptionHandler(ResponseStatusException.class)
// public ResponseEntity<?>
// handleResponseStatusException(ResponseStatusException ex, HttpServletRequest
// request) {
// return ResponseEntity
// .status(ex.getStatusCode())
// .body(Map.of(
// "timestamp", java.time.LocalDateTime.now(),
// "status", ex.getStatusCode().value(),
// "error", ex.getReason(),
// "message", ex.getReason(),
// "path", request.getRequestURI()
// ));
// }
//
// @ExceptionHandler(Exception.class)
// public ResponseEntity<?> handleGeneralException(Exception ex) {
// return ResponseEntity
// .status(HttpStatus.INTERNAL_SERVER_ERROR)
// .body(Map.of(
// "timestamp", java.time.LocalDateTime.now(),
// "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
// "error", "Internal Server Error",
// "message", ex.getMessage()
// ));
// }
// }
