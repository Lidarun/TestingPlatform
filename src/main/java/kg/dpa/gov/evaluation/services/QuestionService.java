package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    void deleteById(int id);
    void create(Question question);
    Optional<Question> findById(int id);
    void update(Question updatedQuestion);
    Page<Question> getItems(Pageable pageable);
    List<Question> findAllByCourseID(long id);
}
