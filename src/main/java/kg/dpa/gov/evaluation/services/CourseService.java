package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Course;

import java.util.List;

public interface CourseService {

    void create(Course course);

    List<Course> findAll();

    void deleteById(long id);

    Course findById(long id);

    void update(long id, Course updatedCourse);

    List<Course> findAllByState(boolean state);

    List<Course> findAllByUserAccess(String username);

}
