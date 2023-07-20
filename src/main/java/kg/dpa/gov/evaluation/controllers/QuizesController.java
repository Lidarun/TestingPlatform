package kg.dpa.gov.evaluation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{courseId}/module/{moduleId}")
    public ResponseEntity<List<Question>> showQuestion(@PathVariable("courseId") long courseId,
                                                       @PathVariable("moduleId") long moduleId) {
        List<Question> questions = questionService.findAllByModuleID(moduleId);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    @PostMapping("/{id}")
    public String submitAnswer(@RequestParam("answer") int answer,
                               @PathVariable("id") int id) {
//        List<Question> questions = questionService.findAllByCourseID(id);
//
//        if (currentIndexQuestionList < questions.size()) {
//            if (answer == questions.get(currentIndexQuestionList).getCorrectAnswer())
//                result++;
//
//            currentIndexQuestionList++;
//        }
//
        return "redirect:/quiz/" + id;
    }
}


