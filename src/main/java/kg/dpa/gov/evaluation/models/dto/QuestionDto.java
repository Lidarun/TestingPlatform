package kg.dpa.gov.evaluation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto implements Serializable {
    private long id;
    private String question;
    private String correctAnswer;
    private List<String> options;

    @Override
    public String toString() {
        return "QuestionDto{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", options=" + options +
                '}';
    }
}
