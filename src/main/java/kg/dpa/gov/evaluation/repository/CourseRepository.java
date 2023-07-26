package kg.dpa.gov.evaluation.repository;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByState(boolean state);

}
