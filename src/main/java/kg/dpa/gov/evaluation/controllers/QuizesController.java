package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizesController {
    private final QuestionService questionService;
    private int currentIndexQuestionList = 0;
    private int result = 0;

    public QuizesController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable("id") int id,
                               Model model) {
        List<Question> questionList = questionService.findAllByCourseID(id);
        if (currentIndexQuestionList > questionList.size()) currentIndexQuestionList = 0;

        if (currentIndexQuestionList < questionList.size()) {
            Question question = questionList.get(currentIndexQuestionList);
            model.addAttribute("id", id);
            model.addAttribute("question", question);

            return "pages/quiz";
        }

        model.addAttribute("result", result);
        model.addAttribute("quizSize", questionList.size());

        result = 0;
        currentIndexQuestionList = 0;

        return "pages/result";
    }

    @PostMapping("/{id}")
    public String submitAnswer(@RequestParam("answer") int answer,
                               @PathVariable("id") int id) {
        List<Question> questions = questionService.findAllByCourseID(id);

        if (currentIndexQuestionList < questions.size()) {
            if (answer == questions.get(currentIndexQuestionList).getCorrectAnswer())
                result++;

            currentIndexQuestionList++;
        }

        return "redirect:/quiz/" + id;
    }
}

