package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name = "tb_questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    Course course;

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

//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private Language lang;

    public Question() {
        this.options = new ArrayList<>();
    }

    public boolean isCorrect(Integer answer) {
        return answer != null && answer.equals(correctAnswer);
    }}
