package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.dto.QuestionDto;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @PostMapping()
    public ResponseEntity<?> submitAnswer(@RequestBody Map<Integer, String> userAnswers) {
        System.out.println("JSON"+userAnswers);

        for (Map.Entry<Integer, String> entry : userAnswers.entrySet()) {
            int questionId = entry.getKey();
            String selectedAnswer = entry.getValue();

            System.out.println("Question ID: " + questionId);
            System.out.println("Selected Answer: " + selectedAnswer);
        }

        return new ResponseEntity<>("Ответы приняты успешно", HttpStatus.OK);
    }

}


