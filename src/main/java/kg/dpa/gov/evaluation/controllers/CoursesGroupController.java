package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
