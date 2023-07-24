package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.mappers.QuestionMapper;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.dto.QuestionDto;
import kg.dpa.gov.evaluation.repository.ModuleRepository;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRep;
    private final ModuleRepository moduleRep;
    private final QuestionMapper mapper;

    public QuestionServiceImpl(QuestionRepository questionRep, ModuleRepository moduleRep, QuestionMapper mapper) {
        this.questionRep = questionRep;
        this.moduleRep = moduleRep;
        this.mapper = mapper;
    }


    @Override
    public void create(Question question) {
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
    public List<QuestionDto> findAllByModuleID(long id) {
        Optional<Module> module = moduleRep.findById(id);
        List<Question> questions = questionRep.findAllByModule(module.get());

        return questions.stream()
                .map(q -> {
                    QuestionDto dto = null;
                    dto = mapper.questionToQuestionDto(q);
                    return dto;
                }).collect(Collectors.toList());
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
