package com.id.akn.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // Import cái này
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalException {

    // Helper method để tạo response JSON chuẩn
    private ResponseEntity<ErrorDetails> buildResponse(String message, String details, HttpStatus status) {
        ErrorDetails err = new ErrorDetails(message, details, LocalDateTime.now());
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON) // QUAN TRỌNG: Ép kiểu về JSON
                .body(err);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException ue, WebRequest req){
        return buildResponse(ue.getMessage(), req.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LaptopException.class)
    public ResponseEntity<ErrorDetails> LaptopExceptionHandler(LaptopException ue, WebRequest req){
        return buildResponse(ue.getMessage(), req.getDescription(false), HttpStatus.BAD_REQUEST);
    }
alException {

    // Helper method để tạo response JSON chuẩn
    private ResponseEntity<ErrorDetails> buildResponse(String message, String details, HttpStatus status) {
        ErrorDetails err = new ErrorDetails(message, details, LocalDateTime.now());
        return ResponseEntity.status(status)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDetails> handleIOException(IOException ex, WebRequest req) {
        return buildResponse(ex.getMessage(), req.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<ErrorDetails> CartItemExceptionHandler(CartItemException ue, WebRequest req){
        return buildResponse(ue.getMessage(), req.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OsVersionException.class)
    public ResponseEntity<ErrorDetails> OsVersionExceptionHandler(OsVersionException ue, WebRequest req){
        return buildResponse(ue.getMessage(), req.getDescription(false), HttpStatus.BAD_REQUEST);
    }
alException {

    // Helper method để tạo response JSON chuẩn
    private ResponseEntity<ErrorDetails> buildResponse(String message, String details, HttpStatus status) {
        ErrorDetails err = new ErrorDetails(message, details, LocalDateTime.now());
        return ResponseEntity.status(status)
    @ExceptionHandler(BrandException.class)
    public ResponseEntity<ErrorDetails> BrandExceptionHandler(BrandException ue, WebRequest req){
        return buildResponse(ue.getMessage(), req.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorDetails> OrderExceptionHandler(OrderException ue, WebRequest req){
        return buildResponse(ue.getMessage(), req.getDescription(false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException e, WebRequest req){
        return buildResponse(e.getMessage(), req.getDescription(false), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        });
        return buildResponse(errors.toString(), "Validation failed", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Endpoint not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    // Đây là chỗ gây lỗi trong log của bạn
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception e, WebRequest req){
        // Fix lỗi HttpMediaTypeNotAcceptableException bằng cách ép content type JSON
        return buildResponse(e.getMessage(), req.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}