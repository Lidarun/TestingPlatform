package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.models.dto.UserDto;
import kg.dpa.gov.evaluation.services.CourseService;
import kg.dpa.gov.evaluation.services.ResultHandler;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/results")
public class ResultsController {
    private final UserService userService;
    private final CourseService courseService;
    private final ResultHandler resultHandler;

    public ResultsController(UserService userService,
                             CourseService courseService,
                             ResultHandler resultHandler) {
        this.userService = userService;
        this.courseService = courseService;
        this.resultHandler = resultHandler;
    }

    @GetMapping
    private String showPage(Model model) {

        List<User> userList = userService.findAll();
        List<Course> courseList = courseService.findAll();

        model.addAttribute("users", userList);
        model.addAttribute("courses", courseList);

        return "dashboard/results";
    }

    @GetMapping("/filter/{courseId}")
    private String showFilterByCoursePage(@PathVariable long courseId,
                                          Model model) {

        List<User> userList = userService.findAllByCourseId(courseId);
        List<Course> courseList = courseService.findAll();

        model.addAttribute("users", userList);
        model.addAttribute("courses", courseList);

        return "dashboard/results-filters/users-by-course";
    }

    //    TODO
    @GetMapping("/filter/{courseId}/module/{moduleId}")
    private String showResultsByModule(@PathVariable long courseId,
                                       @PathVariable long moduleId,
                                          Model model) {

        System.out.println("ID course: "+ courseId);
        System.out.println("ID module: "+ moduleId);

        List<User> userList = userService.findAllByCourseId(courseId);
        List<Course> courseList = courseService.findAll();

        List<UserDto> usersWithResults = resultHandler.countResultsForUsersByModule(userList, moduleId);
        usersWithResults.forEach(System.out::println);

        model.addAttribute("users", usersWithResults);
        model.addAttribute("courses", courseList);

        return "dashboard/results-filters/users-with-results";
    }

}
