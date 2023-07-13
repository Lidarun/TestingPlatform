package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    void create(Course course);
    List<Course> findAll();
    void deleteById(long id);
    Optional<Course> findById(long id);
    void update(long id, Course updatedCourse);
    List<Course> findAllByState(boolean state);
}
