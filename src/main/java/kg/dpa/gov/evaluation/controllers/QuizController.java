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

    public QuizController() {
        questions = new ArrayList<>();
        // Добавьте вопросы в список
        // Например:
        questions.add(new Question("Какой год был основан Java?", Arrays.asList("1995", "2000", "1985", "1970"), 0));
        questions.add(new Question("Что такое JVM?", Arrays.asList("Java Virtual Machine", "Java Virtual Memory", "Java Validation Machine", "Java Verification Machine"), 0));
        currentQuestionIndex = 0;
    }

    @GetMapping()
    public String showQuestion(Model model) {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            model.addAttribute("question", currentQuestion);
            return "question";
        } else {

            currentQuestionIndex = 0;
            return "result";
        }
    }

    @PostMapping()
    public String submitAnswer(Integer answer) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean isCorrect = currentQuestion.isCorrect(answer != null ? answer : -1);
        // Обработка ответа, например, подсчет количества правильных ответов
        // Здесь вы можете использовать сервисы или репозитории для сохранения результатов и других операций с данными

        currentQuestionIndex++;
        return "redirect:/quiz";
    }
}
