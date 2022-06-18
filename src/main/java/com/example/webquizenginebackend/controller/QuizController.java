package com.example.webquizenginebackend.controller;

import com.example.webquizenginebackend.dto.QuizDTO;
import com.example.webquizenginebackend.entity.CompletedQuiz;
import com.example.webquizenginebackend.facade.QuizFacade;
import com.example.webquizenginebackend.payload.request.Answer;
import com.example.webquizenginebackend.payload.response.QuizAnswerResponse;
import com.example.webquizenginebackend.payload.response.QuizPage;
import com.example.webquizenginebackend.service.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@RestController
public class QuizController {


    private final QuizService quizService;
    private final QuizFacade quizFacade;

    public QuizController(QuizService quizService, QuizFacade quizFacade) {

        this.quizService = quizService;
        this.quizFacade = quizFacade;
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable Long id, Principal principal){
        QuizDTO quizDTO = quizService.getQuizById(id);

        return Objects.nonNull(quizDTO) ?
                new ResponseEntity<>(quizDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @GetMapping("/api/quizzes/completed")
    public Page<CompletedQuiz> getAllCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(defaultValue = "completedAt") String sortBy,
                                                      Principal principal){
        var completedQuizPage = quizService.getAllCompetedQuizzes(principal,page,pageSize,sortBy);
        return completedQuizPage;



    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<QuizPage> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy

    ){
        var quizPage = quizService.getAllQuizzes(page,pageSize,sortBy);
        return new ResponseEntity<>(quizPage,new HttpHeaders(),HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<QuizDTO> createNewQuiz(@Valid @RequestBody QuizDTO quizDTO,Principal principal){
        quizDTO = quizService.createQuiz(quizDTO,principal);
        return new ResponseEntity<>(quizDTO,HttpStatus.OK);
    }



    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<QuizAnswerResponse> postAnswerToQuiz(@PathVariable Long id, @RequestBody Answer quizAnswerRequest, Principal principal){
        QuizAnswerResponse response = quizService.postAnswerToQuiz(principal,id,quizAnswerRequest);
        if (response == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable Long id, Principal principal){
        quizService.deleteQuiz(id,principal);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
