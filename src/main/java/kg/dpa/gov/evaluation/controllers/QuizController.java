package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int result;

    public QuizController() {
        questions = new ArrayList<>();
        questions.add(new Question("В каком году был основан Java?", Arrays.asList("1995", "2000", "1985", "1970"), 0));
        questions.add(new Question("Что такое JVM?", Arrays.asList("Java Virtual Machine", "Java Virtual Memory", "Java Validation Machine", "Java Verification Machine"), 0));
        currentQuestionIndex = 0;
    }

    @GetMapping()
    public String showQuestion(Model model) {
        if (currentQuestionIndex > questions.size()) currentQuestionIndex = 0;
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            model.addAttribute("result", result);
            model.addAttribute("quizSize", questions.size());
            model.addAttribute("question", currentQuestion);
            return "question";
        }

        model.addAttribute("result", result);
        model.addAttribute("quizSize", questions.size());

        result = 0;
        currentQuestionIndex = 0;

        return "result";
    }

    @PostMapping()
    public String submitAnswer(@RequestParam("correctAnswer") Integer answer, Model model) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean check = currentQuestion.isCorrect(answer != null ? answer : -1);

        currentQuestionIndex++;
        System.out.println(currentQuestionIndex);

        if (check) {
            result++;
            return "redirect:/quiz";

        }else {
            model.addAttribute("rightAnswer",
                    currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
            return "checker";
        }
    }
}
