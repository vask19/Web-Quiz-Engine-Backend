package com.example.webquizenginebackend.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "completed_quiz")
public class CompletedQuiz {
    @Column
    @JsonIgnore
    private Long userId;
    @Column
    private LocalDateTime completedAt;
    @Column
    private Long id;
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long q;

}
