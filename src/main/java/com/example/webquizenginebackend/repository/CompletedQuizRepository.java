package com.example.webquizenginebackend.repository;


import com.example.webquizenginebackend.entity.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, LocalDateTime> {




    Page<CompletedQuiz> findAllByUserId(Long id, Pageable pageable);
}
