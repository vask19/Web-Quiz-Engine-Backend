package com.example.webquizenginebackend.repository;



import com.example.webquizenginebackend.entity.Quiz;
import com.example.webquizenginebackend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {




    Optional<Quiz> findById(Long id);

    Quiz save(Quiz quiz);

    Optional<Quiz> findQuizByIdAndUser(Long quizId, Users user);



}
