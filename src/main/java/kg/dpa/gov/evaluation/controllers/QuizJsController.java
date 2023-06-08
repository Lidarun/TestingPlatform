package kg.dpa.gov.evaluation.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasAnyAuthority()")
//@RequestMapping("/quiz")
public class QuizJsController {

    @GetMapping()
    private String showQuiz(Model model) {
        return "questionJS";
    }
}