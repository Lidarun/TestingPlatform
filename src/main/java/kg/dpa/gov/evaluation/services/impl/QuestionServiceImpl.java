package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.ModuleRepository;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRep;
    private final ModuleRepository moduleRep;

    public QuestionServiceImpl(QuestionRepository questionRep, ModuleRepository moduleRep) {
        this.questionRep = questionRep;
        this.moduleRep = moduleRep;
    }


    @Override
    public void create(Question question) {
        question.getOptions().remove(question.getCorrectAnswer());
        questionRep.save(question);
    }

    @Override
    public Optional<Question> findById(int id) {
        return questionRep.findById(id);
    }

    @Override
    public Page<Question> getItems(Pageable pageable) {
        return questionRep.findAll(pageable);
    }

    @Override
    public List<Question> findAllByModuleID(long id) {
        Optional<Module> module = moduleRep.findById(id);
        return module.map(questionRep::findAllByModule).orElse(null);
    }

    @Override
    public void update(Question updatedQuestion) {
        if (questionRep.existsById((int) updatedQuestion.getId()))
            questionRep.saveAndFlush(updatedQuestion);
    }

    @Override
    public void deleteById(int id) {
        questionRep.deleteById(id);
    }
}
