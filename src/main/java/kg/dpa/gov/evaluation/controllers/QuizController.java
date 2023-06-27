package kg.dpa.gov.evaluation.controllers;

import jakarta.validation.constraints.NotNull;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuestionService service;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int result;

    public QuizController(QuestionService service) {
        this.service = service;
        this.questions = new ArrayList<>();
        currentQuestionIndex = 0;
        result = 0;
    }

    @GetMapping()
    public String showQuestion(@CookieValue("lang") String langCookieValue,
                               Model model) {
        questions = service.findAllByLang(langCookieValue);
        if (currentQuestionIndex > questions.size()) currentQuestionIndex = 0;
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            model.addAttribute("question", currentQuestion);
            model.addAttribute("result", result);
            model.addAttribute("quizSize", questions.size());

            return "question";
        }

//        if (questions.isEmpty()) return "redirect:/";

        model.addAttribute("result", result);
        model.addAttribute("quizSize", questions.size());

        result = 0;
        currentQuestionIndex = 0;

        return "result";
    }

    @PostMapping()
    public String submitAnswer(@RequestParam("correctAnswer") @NotNull Integer answer,
                               Model model) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean check = currentQuestion.isCorrect(answer);

        currentQuestionIndex++;

        if (check) {
            result++;
            model.addAttribute("rightAnswer",
                    currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
            model.addAttribute("why",
                    currentQuestion.getAnswerExplain());
            return "right-checker";

        } else {
            model.addAttribute("rightAnswer",
                    currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
            model.addAttribute("wrongAnswer",
                    currentQuestion.getOptions().get(answer));
            model.addAttribute("why",
                    currentQuestion.getAnswerExplain());
            return "wrong-checker";
        }
    }
}

