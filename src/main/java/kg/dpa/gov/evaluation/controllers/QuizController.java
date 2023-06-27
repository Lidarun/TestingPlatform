package kg.dpa.gov.evaluation.controllers;

import jakarta.servlet.http.HttpServletRequest;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.utils.LanguageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuestionRepository repository;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int result;

    public QuizController(QuestionRepository repository) {
        this.repository = repository;
        this.questions = new ArrayList<>();
        currentQuestionIndex = 0;
        result = 0;
    }

    @GetMapping()
    public String showQuestion(Model model, HttpServletRequest request) {
        String acceptLanguage = request.getHeader("Accept-Language");
        String activeLanguage = LanguageUtils.getActiveLanguage(acceptLanguage);
        System.out.println(activeLanguage);

        questions = repository.findAll();
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
    public String submitAnswer(@RequestParam("correctAnswer") Integer answer, Model model) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean check = currentQuestion.isCorrect(answer != null ? answer : -1);

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

