package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.services.CourseService;
import kg.dpa.gov.evaluation.services.ResultHandler;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CourseService courseService;
    private final ResultHandler resultHandler;

    public UserController(UserService userService,
                          CourseService courseService,
                          ResultHandler resultHandler) {
        this.userService = userService;
        this.courseService = courseService;
        this.resultHandler = resultHandler;
    }

    @GetMapping("/{userId}")
    private String showPage(@PathVariable long userId,
                            Model model) {
        User user = userService.findById(userId);
        List<Course> courseList = courseService.findAllByUserAccess(user.getUsername()).stream()
                .filter(Course::isAccess).collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("courses", courseList);

        return "pages/user-page";
    }

    @GetMapping("/{userId}/module/{moduleId}")
    private String showPage(@PathVariable long userId,
                            @PathVariable long moduleId,
                            Model model) {
        List<Question> questions = resultHandler.findAllResultsByUserIdAndModuleId(userId, moduleId);

        model.addAttribute("questions", questions);

        return "pages/user-results";
    }

}
