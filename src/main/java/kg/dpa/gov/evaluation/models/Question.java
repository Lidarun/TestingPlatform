package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
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
    private String question;

    @ElementCollection
    @Fetch(value = FetchMode.JOIN)
    private List<String> options;
    private int correctAnswer;
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

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }
}
