package com.example.webquizenginebackend.service;

import com.example.webquizenginebackend.dto.QuizDTO;
import com.example.webquizenginebackend.entity.CompletedQuiz;
import com.example.webquizenginebackend.entity.Quiz;
import com.example.webquizenginebackend.entity.Users;
import com.example.webquizenginebackend.exception.QuizNotFoundException;
import com.example.webquizenginebackend.exception.QuizWithPrincipalNotFound;
import com.example.webquizenginebackend.facade.QuizFacade;
import com.example.webquizenginebackend.facade.QuizPageFacade;
import com.example.webquizenginebackend.payload.request.Answer;
import com.example.webquizenginebackend.payload.response.QuizAnswerResponse;
import com.example.webquizenginebackend.payload.response.QuizPage;
import com.example.webquizenginebackend.repository.CompletedQuizRepository;
import com.example.webquizenginebackend.repository.QuizPagingAndSortedRepository;
import com.example.webquizenginebackend.repository.QuizRepository;
import com.example.webquizenginebackend.repository.UserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;


@Service
public class QuizService {
    private final QuizFacade quizFacade;
    private final QuizRepository quizRepository;
    private final QuizPageFacade quizDTOPageFacade;
    private final QuizPagingAndSortedRepository quizPagingAndSortedRepository;

    private final CompletedQuizRepository completedQuizRepository;
    private final UserRepository userRepository;
    public QuizService(QuizFacade quizFacade, QuizRepository quizRepository, QuizPageFacade quizDTOPageFacade, QuizPagingAndSortedRepository quizPagingAndSortedRepository, CompletedQuizRepository completedQuizRepository, UserRepository userRepository) {
        this.quizFacade = quizFacade;
        this.quizRepository = quizRepository;
        this.quizDTOPageFacade = quizDTOPageFacade;
        this.quizPagingAndSortedRepository = quizPagingAndSortedRepository;
        this.completedQuizRepository = completedQuizRepository;
        this.userRepository = userRepository;
    }


    //TODO
    public QuizDTO getQuizById(Long id){
        Optional<Quiz> quiz = quizRepository.findById(id);
        System.out.println(11);
        return quiz.map(quizFacade::quizToQuizDTO).orElse(null);

    }

    //відправляє сторінку з нарізаними вікторинами
    //фасад мапить нову сторінку з дто замість ент
    public QuizPage getAllQuizzes(Integer pageNo, Integer pageSize, String sortedBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
         var  pagedResult = quizPagingAndSortedRepository.findAll(paging);
         return quizDTOPageFacade.pageToQuizDTOPAge(pagedResult);
    }

    //перевіряє відповіді на конкретну вікторину
    public QuizAnswerResponse postAnswerToQuiz(Principal principal , Long id, Answer quizAnswerRequest) {
       Quiz quiz = quizRepository.findById(id).orElseThrow(()-> new QuizNotFoundException("Quiz not found"));
       QuizAnswerResponse quizAnswerResponse;
        if (CollectionUtils.isEqualCollection(quizAnswerRequest.getAnswer(),quiz.getAnswer()))
            quizAnswerResponse = savingAnswerToQuiz(principal,quiz);

        else{
            quizAnswerResponse = new QuizAnswerResponse();
            quizAnswerResponse.setSuccess(false);
            quizAnswerResponse.setFeedback("Wrong answer! Please, try again.");
        }
        return quizAnswerResponse;
    }
    //зберігає відповідь на лотерею
    private QuizAnswerResponse savingAnswerToQuiz(Principal principal,Quiz quiz){
        QuizAnswerResponse quizAnswerResponse;
        Users user = getUserByPrincipal(principal);

        CompletedQuiz completedQuiz = CompletedQuiz
                .builder()
                .id(quiz.getId())
                .userId(user.getId())
                .completedAt(LocalDateTime.now())
                .build();
        completedQuizRepository.save(completedQuiz);
        quizAnswerResponse = new QuizAnswerResponse();
        quizAnswerResponse.setSuccess(true);
        quizAnswerResponse.setFeedback("Congratulations, you're right!");
        return quizAnswerResponse;
    }


    //пошук діючого користувача
    public Users getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Users not found with username"));


    }

    //видалення вікторини тільки для діючого користувача
    public void deleteQuiz(Long id,Principal principal){
        Quiz quiz = getQuizByIdAndPrincipal(id,principal);
        quizRepository.delete(quiz);
    }

    //пошук вікторини для діючого користувача (потвібний для видалення)
    public Quiz getQuizByIdAndPrincipal(Long quizId,Principal principal){
        Users user = getUserByPrincipal(principal);
        Quiz quiz;
        if (quizRepository.findById(quizId).isEmpty()){
            throw new QuizNotFoundException("Quiz cannot be found for username: " );
        }

        quiz = quizRepository.findQuizByIdAndUser(quizId,user)
                .orElseThrow(() -> new QuizWithPrincipalNotFound("Quiz cannot be found" ));

        return quiz;
    }




    //створення вікторини
    public QuizDTO createQuiz(QuizDTO quizDTO,Principal principal){
        Users user = getUserByPrincipal(principal);
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setAnswer(quizDTO.getAnswer());
        quiz.setOptions(quizDTO.getOptions());
        quiz.setTitle(quizDTO.getTitle());
        quiz.setText(quizDTO.getText());
        quizRepository.save(quiz);
        System.out.println(quiz.getId());


        return quizFacade.quizToQuizDTO(quiz);
    }

    public Page<CompletedQuiz> getAllCompetedQuizzes(Principal principal, Integer pageNo, Integer pageSize,String  sortedBy) {
        Users user = getUserByPrincipal(principal);
        Pageable paging = PageRequest.of(pageNo, pageSize, DESC, sortedBy);
        var page = completedQuizRepository.findAllByUserId(user.getId(),paging);
        return page;



    }
}
//            return new  ResponseEntity<>(new QuizAnswerResponse(true,"Congratulations, you're right!"),HttpStatus.OK);
