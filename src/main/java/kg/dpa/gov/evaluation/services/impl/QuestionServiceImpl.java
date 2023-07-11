package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.enums.Language;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository repository;

    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Question question) {
        repository.save(question);
    }

    @Override
    public Optional<Question> findById(int id) {
        return repository.findById(id);
    }

//    @Override
//    public List<Question> findAllByLang(String lang) {
//        if (lang.equals(Language.KYR.getLang()))
//            return repository.findAllByLang(Language.KYR);
//        else return repository.findAllByLang(Language.RUS);
//    }

    @Override
    public Page<Question> getItems(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void update(Question updatedQuestion) {
        if (repository.existsById(updatedQuestion.getId()))
            repository.saveAndFlush(updatedQuestion);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
