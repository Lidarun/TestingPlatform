package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.dto.QuestionDto;
import kg.dpa.gov.evaluation.services.QuestionService;
import kg.dpa.gov.evaluation.services.ResultHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizesController {
    private final QuestionService questionService;
    private final ResultHandler resultHandler;

    public QuizesController(QuestionService questionService, ResultHandler resultHandler) {
        this.questionService = questionService;
        this.resultHandler = resultHandler;
    }

    @GetMapping("/{courseId}/module/{moduleId}")
    public ResponseEntity<List<QuestionDto>> showQuestion(@PathVariable("courseId") long courseId,
                                                       @PathVariable("moduleId") long moduleId) {
        List<QuestionDto> questions = questionService.findAllByModuleID(moduleId);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> submitAnswer(@RequestBody Map<Integer, String> userAnswers,
                                          Authentication authentication) {

        resultHandler.setResults(userAnswers, authentication);

        return new ResponseEntity<>("Ответы приняты успешно", HttpStatus.OK);
    }

}


