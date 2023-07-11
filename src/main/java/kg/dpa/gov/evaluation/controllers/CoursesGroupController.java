package kg.dpa.gov.evaluation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses-group")
public class CoursesGroupController {

    @GetMapping
    private String showPage(Model model) {
        return "pages/courses";
    }
}
