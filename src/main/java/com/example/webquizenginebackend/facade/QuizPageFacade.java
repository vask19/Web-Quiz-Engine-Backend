package com.example.webquizenginebackend.facade;



import com.example.webquizenginebackend.entity.Quiz;
import com.example.webquizenginebackend.payload.response.QuizPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QuizPageFacade {

    @Autowired
    private QuizFacade quizFacade;

    public QuizPage pageToQuizDTOPAge(Page<Quiz> page){

        var content= page.getContent()
                .stream()
                .map(quizFacade::quizToQuizDTO).collect(Collectors.toList());

        QuizPage quizDTOPAge = QuizPage.builder()
                .totalPages(page.getTotalPages())
                .content(content)
                .empty(page.isEmpty())
                .first(page.isFirst())
                .last(page.isLast())
                .totalElements(page.getTotalElements())
                .sort(page.getSort())
                .pageable(page.getPageable())
                .build();

        return quizDTOPAge;
    }
}
