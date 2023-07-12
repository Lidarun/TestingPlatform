package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.CourseRepository;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.services.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void create(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Optional<Question> findById(int id) {
        return questionRepository.findById(id);
    }

//    @Override
//    public List<Question> findAllByLang(String lang) {
//        if (lang.equals(Language.KYR.getLang()))
//            return repository.findAllByLang(Language.KYR);
//        else return repository.findAllByLang(Language.RUS);
//    }

    @Override
    public Page<Question> getItems(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public List<Question> findAllByCourseID(long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(questionRepository::findAllByCourse).orElse(null);
    }

    @Override
    public void update(Question updatedQuestion) {
        if (questionRepository.existsById(updatedQuestion.getId()))
            questionRepository.saveAndFlush(updatedQuestion);
    }

    @Override
    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }
}
