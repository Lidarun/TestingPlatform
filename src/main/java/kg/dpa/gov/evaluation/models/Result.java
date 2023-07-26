package kg.dpa.gov.evaluation.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private long moduleId;
    private long questionId;
    private long answerIndex;


    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", userId=" + userId +
                ", moduleId=" + moduleId +
                ", questionId=" + questionId +
                ", answerIndex=" + answerIndex +
                '}';
    }
}
