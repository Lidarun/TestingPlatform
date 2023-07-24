package kg.dpa.gov.evaluation.mappers;

import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.dto.QuestionDto;

public interface QuestionMapper {
    QuestionDto questionToQuestionDto(Question question);
}
