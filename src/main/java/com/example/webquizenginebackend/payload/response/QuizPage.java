package com.example.webquizenginebackend.payload.response;


import com.example.webquizenginebackend.dto.QuizDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizPage {
    private Integer totalPages;
    private Long totalElements;
    private boolean last;
    private boolean first;
    private Long number;
    private Long numberOfElements;
    private Long size;
    private boolean empty;
    private Sort sort;
    private Pageable pageable;
    private List<QuizDTO> content;


}
