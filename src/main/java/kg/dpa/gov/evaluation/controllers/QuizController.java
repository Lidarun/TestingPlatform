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
    private int listSize;

    public QuizController(QuestionService service) {
        this.service = service;
        this.questions = new ArrayList<>();
    }

    @GetMapping()
    public String showQuestion(@CookieValue("lang") String langCookieValue,
                               Model model) {
        questions = service.findAllByLang(langCookieValue);
        listSize = questions.size();
        if (currentQuestionIndex > questions.size()) currentQuestionIndex = 0;
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            model.addAttribute("question", currentQuestion);
            model.addAttribute("result", result);
            model.addAttribute("quizSize", questions.size());

            return "question";
        }

//        if (questions.isEmpty()) return "redirect:/";

        String resultInfo = calculate(result, questions.size());

        model.addAttribute("result", result);
        model.addAttribute("quizSize", questions.size());
        model.addAttribute("resultInfo", resultInfo);

        if (currentQuestionIndex < 5 && currentQuestionIndex > 0)
            model.addAttribute("resMsg2", "resMsg2");
        else
            model.addAttribute("resMsg3", "resMsg3");

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

        System.out.println(listSize);
        System.out.println(currentQuestionIndex);

        if (check) {
            result++;
            model.addAttribute("rightAnswer",
                    currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
            model.addAttribute("why",
                    currentQuestion.getAnswerExplain());

            if (listSize == currentQuestionIndex)
                model.addAttribute("result", "result");
            else
                model.addAttribute("next", "next");

            return "right-checker";

        } else {
            model.addAttribute("rightAnswer",
                    currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
            model.addAttribute("wrongAnswer",
                    currentQuestion.getOptions().get(answer));
            model.addAttribute("why",
                    currentQuestion.getAnswerExplain());
            if (listSize == currentQuestionIndex)
                model.addAttribute("result", "result");
            else
                model.addAttribute("next", "next");
            return "wrong-checker";
        }
    }

    private String calculate(int result, int size) {
        if (result ==  size)
            return "Поздравляем! Вы достигли отличного результата!";

        else if (result >= size - (size * 20 / 100))
            return "Хорошая работа! Ваши знания впечатляют!";

        else if (result >= size - (size * 30 / 100))
            return "Неплохо, но есть возможность улучшиться!";

        return "Вам стоит поработать над своими знаниями.";
    }
}

