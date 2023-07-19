package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/modules")
public class ModulesController {
    private final CourseService courseService;

    public ModulesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    private String showPage(Model model){
        List<Course> courses = courseService.findAll();
        model.addAttribute("formModule", new Module());
        model.addAttribute("courses", courses);
        return "dashboard/modules";
    }
}
