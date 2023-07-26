package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.mappers.QuestionMapper;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.dto.QuestionDto;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.services.ModuleService;
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
    private final ModuleService moduleService;
    private final QuestionMapper mapper;

    public QuestionServiceImpl(QuestionRepository questionRep,
                               ModuleService moduleService,
                               QuestionMapper mapper) {
        this.questionRep = questionRep;
        this.moduleService = moduleService;
        this.mapper = mapper;
    }


    @Override
    public void create(Question question) {
        questionRep.save(question);
    }

    @Override
    public Optional<Question> findById(long id) {
        return questionRep.findById((int) id);
    }

    @Override
    public Page<Question> getItems(Pageable pageable) {
        return questionRep.findAll(pageable);
    }

    @Override
    public List<QuestionDto> findAllByModuleID(long id) {
        Module module = moduleService.findById(id);
        List<Question> questions = questionRep.findAllByModule(module);

        return questions.stream()
                .map(q -> {
                    QuestionDto dto;
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
