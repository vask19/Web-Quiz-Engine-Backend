package com.example.webquizenginebackend.repository;

import com.example.webquizenginebackend.entity.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizPagingAndSortedRepository
        extends PagingAndSortingRepository<Quiz,Long>   {



}
