package kg.dpa.gov.evaluation.controllers;

import jakarta.validation.Valid;
import kg.dpa.gov.evaluation.models.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/form")
public class QuizController {

    @GetMapping
    private String showPage(Model model) {
        model.addAttribute("quizForm", new Subject());
        return "form";
    }

    @PostMapping
    private String getFormInfos(@ModelAttribute("quizForm") @Valid Subject subject,
            Model model) {

        List<String> list = new ArrayList<>();
        list.add("Яблоки");
        list.add("Вата");
        list.add("Незнаю");

        model.addAttribute("list", list);
        model.addAttribute(subject);

        System.out.println(subject.toString());
        return "form";
    }
}