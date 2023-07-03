package kg.dpa.gov.evaluation.repository;

import kg.dpa.gov.evaluation.enums.Language;
import kg.dpa.gov.evaluation.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByLang(Language lang);
    Page<Question> findAll(Pageable pageable);
}
