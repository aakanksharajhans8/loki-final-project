package com.cams.notification.register.advice;
import java.util.HashMap; import java.util.Map;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@ControllerAdvice
public class GlobalExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,Object>> handleValidationExceptions(MethodArgumentNotValidException ex){
    Map<String,String> errors=new HashMap<>(); ex.getBindingResult().getFieldErrors().forEach(err->errors.put(err.getField(),err.getDefaultMessage()));
    Map<String,Object> body=new HashMap<>(); body.put("message","Validation failed"); body.put("errors",errors);
    return ResponseEntity.badRequest().body(body);
  }
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String,Object>> handleIllegalArgument(IllegalArgumentException ex){
    Map<String,Object> body=new HashMap<>(); body.put("message",ex.getMessage()); return ResponseEntity.badRequest().body(body);
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String,Object>> handleGeneral(Exception ex){
    Map<String,Object> body=new HashMap<>(); body.put("message","Internal error"); body.put("error",ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
