package com.example.webquizenginebackend.facade;


import com.example.webquizenginebackend.dto.QuizDTO;
import com.example.webquizenginebackend.entity.Quiz;
import lombok.Data;
import org.springframework.stereotype.Component;
@Data
@Component
public class QuizFacade {
    public QuizDTO quizToQuizDTO(Quiz quiz){
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setText(quiz.getText());
        quizDTO.setOptions(quiz.getOptions());
        quizDTO.setTitle(quiz.getTitle());


        return quizDTO;

    }
}
