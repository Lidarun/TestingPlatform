package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Question;
import org.springframework.validation.ObjectError;

public interface QuestionValidationService {
    ObjectError checkFields(Question question);
}
