package com.example.webquizenginebackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {
    @NotEmpty
    @NotBlank
    private String title;
    @NotEmpty
    @NotBlank
    private String text;
    @NotEmpty
    @Size(min = 2)
    private List<String > options;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> answer;
    private Long id;




}
