package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.repository.CourseRepository;
import kg.dpa.gov.evaluation.services.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRep;

    public CourseServiceImpl(CourseRepository courseRep) {
        this.courseRep = courseRep;
    }

    @Override
    public void create(Course course) {
        courseRep.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRep.findAll();
    }

    @Override
    public void deleteById(long id) {
        if (courseRep.findById(id).isPresent())
            courseRep.deleteById(id);
    }

    @Override
    public Optional<Course> findById(long id) {
        return courseRep.findById(id);
    }

    @Override
    public void update(long id, Course updatedCourse) {
        if (courseRep.findById(id).isPresent())
            courseRep.save(updatedCourse);
    }
}
