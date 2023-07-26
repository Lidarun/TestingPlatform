package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.services.CourseService;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses-group")
public class CoursesGroupController {
    private final CourseService service;
    private final UserService userService;

    public CoursesGroupController(CourseService service,
                                  UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    private String showPage(Model model,
                            Authentication authentication) {
        String username = null;
        if (authentication != null)
            username = authentication.getName();

        List<Course> courses = service.findAllByUserAccess(username);

        model.addAttribute("courses", courses);
        return "pages/courses-group";
    }

    @GetMapping("/{id}/modules")
    private String showModulesPage( @PathVariable("id") long courseId,
                                    Model model) {
        Course course = service.findById(courseId);

        if (course != null) {
            List<Module> modules = course.getModules().stream()
                    .filter(Module::isState)
                    .collect(Collectors.toList());
            model.addAttribute("course", course);
            model.addAttribute("modules", modules);
        }

        model.addAttribute("courseId", courseId);

        return "pages/module";
    }

    @PostMapping("/{id}")
    public String redirectToQuiz(@PathVariable("id") long courseId,
                                 @RequestParam String keyCourse,
                                 Authentication authentication) {
        Course course = service.findById(courseId);
        String username = null;

        if (authentication != null) {
            username = authentication.getName();

        System.out.println("COURSE: "+courseId);
        System.out.println("USER: "+username);
        System.out.println("KEY: "+keyCourse);
        }

        if (course != null && course.getKey().equals(keyCourse)) {
            userService.addCourse(username, courseId);
            return "redirect:/courses-group/"+ courseId +"/modules";

        } else {
            return "pages/access-denied";
        }
    }
}
