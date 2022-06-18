package com.example.webquizenginebackend.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswerResponse {

    private boolean success;
    private String feedback;
}
