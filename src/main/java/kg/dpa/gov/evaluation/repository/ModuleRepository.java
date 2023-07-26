package kg.dpa.gov.evaluation.repository;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByCourse(Course course);
    Module findByQuestions(Question question);
}
