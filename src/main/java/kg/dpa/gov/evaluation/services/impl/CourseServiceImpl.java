package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.repository.CourseRepository;
import kg.dpa.gov.evaluation.repository.UserRepository;
import kg.dpa.gov.evaluation.services.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRep;
    private final UserRepository userRep;

    public CourseServiceImpl(CourseRepository courseRep, UserRepository userRep) {
        this.courseRep = courseRep;
        this.userRep = userRep;
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
    public Course findById(long id) {
        return courseRep.findById(id).orElse(null);
    }

    @Override
    public void update(long id, Course updatedCourse) {
        if (courseRep.findById(id).isPresent())
            courseRep.save(updatedCourse);
    }

    @Override
    public List<Course> findAllByState(boolean state) {
        return courseRep.findAllByState(true);
    }

    @Override
    public List<Course> findAllByUserAccess(String username) {
        List<Course> courses = courseRep.findAllByState(true);

        Optional<User> userOptional = userRep.findByUsernameOrEmail(username, username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Set<Course> userCourses = user.getCourses();

            return courses.stream().peek(c -> userCourses.forEach(n -> {
                if (n.getId() == c.getId()) c.setAccess(true);
            }
            )).collect(Collectors.toList());
        }

        return null;
    }
}
