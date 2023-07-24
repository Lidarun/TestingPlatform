package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.dto.QuestionDto;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizesController {
    private final QuestionService questionService;

    public QuizesController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{courseId}/module/{moduleId}")
    public ResponseEntity<List<QuestionDto>> showQuestion(@PathVariable("courseId") long courseId,
                                                       @PathVariable("moduleId") long moduleId) {
        List<QuestionDto> questions = questionService.findAllByModuleID(moduleId);
        questions.forEach(System.out::println);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    @PostMapping("/{id}")
    public String submitAnswer(@RequestParam("answer") int answer,
                               @PathVariable("id") int id) {

        return "redirect:/quiz/" + id;
    }
}


