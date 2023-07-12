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
    private final QuestionService questionService;

    public QuizController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable("id") long id,
                               Model model) {

        List<Question> questions = questionService.findAllByCourseID(id);

        model.addAttribute("questions", questions);

        return "pages/quiz";
    }

//    @PostMapping()
//    public String submitAnswer(@RequestParam("correctAnswer") @NotNull Integer answer,
//                               Model model) {
//        Question currentQuestion = questions.get(currentQuestionIndex);
//        boolean check = currentQuestion.isCorrect(answer);
//
//        currentQuestionIndex++;
//
////        System.out.println(listSize);
////        System.out.println(currentQuestionIndex);
//
//        if (check) {
//            result++;
//            model.addAttribute("rightAnswer",
//                    currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
//            model.addAttribute("why",
//                    currentQuestion.getAnswerExplain());
//
//            if (listSize == currentQuestionIndex)
//                model.addAttribute("result", "result");
//            else
//                model.addAttribute("next", "next");
//
//            return "pages/right-checker";
//
//        } else {
//            model.addAttribute("rightAnswer",
//                    currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
//            model.addAttribute("wrongAnswer",
//                    currentQuestion.getOptions().get(answer));
//            model.addAttribute("why",
//                    currentQuestion.getAnswerExplain());
//            if (listSize == currentQuestionIndex)
//                model.addAttribute("result", "result");
//            else
//                model.addAttribute("next", "next");
//            return "pages/wrong-checker";
//        }
//    }

}

