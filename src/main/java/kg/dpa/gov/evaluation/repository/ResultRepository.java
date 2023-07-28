package kg.dpa.gov.evaluation.repository;

import kg.dpa.gov.evaluation.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    boolean existsByUserIdAndQuestionId(long userId, long questionId);

    List<Result> findAllResultsByUserId(long id);

    List<Result> findAllByModuleId(long moduleId);
}
