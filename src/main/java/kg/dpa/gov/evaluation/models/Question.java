package kg.dpa.gov.evaluation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name = "tb_questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "module_id")
    Module module;

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

    public boolean isCorrect(Integer answer) {
        return answer != null && answer.equals(correctAnswer);
    }}
