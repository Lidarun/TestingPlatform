package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    void create(Question question);

    void deleteById(int id);

    Optional<Question> findById(int id);

    void update(Question updatedQuestion);

    Page<Question> getItems(Pageable pageable);

    List<QuestionDto> findAllByModuleID(long id);

}
