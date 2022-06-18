package com.example.webquizenginebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class QuizWithPrincipalNotFound extends RuntimeException{

    public QuizWithPrincipalNotFound(String message) {
        super(message);
    }
}
