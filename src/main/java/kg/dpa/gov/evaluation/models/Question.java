package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "tb_questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String question;

    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    @Fetch(value = FetchMode.JOIN)
    private List<@NotEmpty String> options;

    private Integer correctAnswer = null;

    @NotEmpty
    private String answerExplain;

    public Question() {
        this.options = new ArrayList<>();
    }
    public Question(String question, List<String> options, int correctAnswer, String answerExplain) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.answerExplain = answerExplain;
    }

    public boolean isCorrect(Integer answer) {
        return answer != null && answer.equals(correctAnswer);
    }}
