package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAllByLang(String lang);
}
