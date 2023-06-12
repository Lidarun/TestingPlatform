package kg.dpa.gov.evaluation.repository;

import kg.dpa.gov.evaluation.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
