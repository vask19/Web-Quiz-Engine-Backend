package com.example.webquizenginebackend.entity;
import com.example.webquizenginebackend.dto.QuizDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private String text;
    @Column
    @ElementCollection
    private List<String > options;
    @Column
    @ElementCollection
    private List<Integer> answer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;
    public Quiz(QuizDTO quizDTO) {
        this.title = quizDTO.getTitle();
        this.text = quizDTO.getText();
        this.options = quizDTO.getOptions();
        this.answer = quizDTO.getAnswer();
    }
}
