package com.uzay.securitygeneltekrarr.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalHandlerException {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        // Hata mesajını ve uygun HTTP durumunu döndür
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404 Not Found
                .body(exception.getMessage()); // İstisna mesajını gövde olarak gönder
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        ArrayList<Object> liste = new ArrayList<>();
        fieldErrors.forEach(fieldError -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("hata mesajı" , fieldError.getDefaultMessage());
            map.put("reddedilen değer",fieldError.getRejectedValue().toString());
            map.put("hatalı alan",fieldError.getField());
            map.put("hata kodu",fieldError.getCode());
            liste.add(map);
        });
        return  ResponseEntity.ok().body(liste);
    };

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?>handleDataIntegrityViolationException(DataIntegrityViolationException exception) {

            if(exception.getMessage().contains("duplicate key value violates unique constraint")) {
                return ResponseEntity.badRequest().body("bu mail zaten kayıtlı");
            }
            else {
                return ResponseEntity.badRequest().body("hata oluştu");

            }




    }






}
