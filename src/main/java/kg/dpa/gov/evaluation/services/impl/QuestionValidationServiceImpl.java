package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.services.QuestionValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

@Service
public class QuestionValidationServiceImpl implements QuestionValidationService {

    @Override
    public ObjectError checkFields(Question question) {
        ObjectError error = null;
        Integer correctAnswer = question.getCorrectAnswer();
        System.out.println("NURi"+correctAnswer);
        if (correctAnswer == null) error =
                new ObjectError("global", "Укажите правильный вариант и язык вопроса");

        return error;
    }
}
