package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/courses-group")
public class CoursesGroupController {
    private final CourseService service;

    public CoursesGroupController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    private String showPage(Model model) {
        model.addAttribute("courses", service.findAllByState(true));
        return "pages/courses";
    }

    @GetMapping("/check/{id}")
    public String redirectToQuiz(@PathVariable("id") long courseId,
                                 @RequestParam("key") String key) {
        Optional<Course> course = service.findById(courseId);

        if (course.isPresent() && course.get().getKey().equals(key)) {
            // Ключ верный, перенаправить пользователя на страницу курса
            return "redirect:/quiz/" + courseId; // Замените "/quiz/" на URL вашей страницы курса
        } else {
            return "redirect:/";
//            return "access_denied";
        }
    }
}
