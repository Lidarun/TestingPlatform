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
import java.util.Optional;

@Controller
@RequestMapping("/courses-group")
public class CoursesGroupController {
    private final CourseService service;
    private final UserService userService;

    public CoursesGroupController(CourseService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    private String showPage(Model model,
                            Authentication authentication) {
        String username = null;
        if (authentication != null)
            username = authentication.getName();
//        System.out.println("USER: "+username);

//        courses.forEach(System.out::println);
//        System.out.println("CHECK: "+courses.isEmpty());
//        System.out.println("CHECK: "+courses.size());

        List<Course> courses = service.findAllByUserAccess(username);

        model.addAttribute("courses", courses);
        return "pages/courses-group";
    }

    @GetMapping("/{id}/modules")
    private String showModulesPage( @PathVariable("id") long courseId,
                                    Model model) {
        Optional<Course> course = service.findById(courseId);

        if (course.isPresent()) {
            List<Module> modules = course.get().getModules();
            model.addAttribute("course", course.get());
            model.addAttribute("modules", modules);
        }

        return "pages/module";
    }

    @PostMapping("/{id}")
    public String redirectToQuiz(@PathVariable("id") long courseId,
                                 @RequestParam String keyCourse,
                                 Authentication authentication) {
        Optional<Course> course = service.findById(courseId);
        String username = null;

        if (authentication != null) {
            username = authentication.getName();

        System.out.println("COURSE: "+courseId);
        System.out.println("USER: "+username);
        System.out.println("KEY: "+keyCourse);
        }

        if (course.isPresent() && course.get().getKey().equals(keyCourse)) {
            userService.addCourse(username, courseId);
            return "redirect:/quiz/" + courseId;

        } else {
            return "pages/access-denied";
        }
    }
}
