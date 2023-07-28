package kg.dpa.gov.evaluation.mappers.impl;

import kg.dpa.gov.evaluation.mappers.EntityMapper;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.dto.QuestionDto;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapperImpl implements EntityMapper<Question, QuestionDto> {

    @Override
    public QuestionDto map(Question question) {
        QuestionDto dto = new QuestionDto();

        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setCorrectAnswer(question.getOptions().get(question.getCorrectAnswer()));

        question.getOptions().remove((int) question.getCorrectAnswer());
        dto.setOptions(question.getOptions()) ;

        return dto;
    }

}
