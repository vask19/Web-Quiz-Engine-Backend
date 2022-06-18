package com.example.webquizenginebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String ms) {
        super(ms);
    }
}
