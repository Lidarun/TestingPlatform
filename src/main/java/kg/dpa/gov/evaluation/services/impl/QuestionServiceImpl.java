package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.enums.Language;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository repository;

    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Question> findAllByLang(String lang) {
        if (lang.equals(Language.KYR.getLang()))
            return repository.findAllByLang(Language.KYR);
        else return repository.findAllByLang(Language.RUS);
    }
}
