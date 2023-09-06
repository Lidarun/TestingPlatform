package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.mappers.EntityMapper;
import kg.dpa.gov.evaluation.mappers.impl.UserMapperImpl;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.*;
import kg.dpa.gov.evaluation.models.dto.UserDto;
import kg.dpa.gov.evaluation.repository.ResultRepository;
import kg.dpa.gov.evaluation.services.ModuleService;
import kg.dpa.gov.evaluation.services.QuestionService;
import kg.dpa.gov.evaluation.services.ResultHandler;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ResultHandlerImpl implements ResultHandler {

    private final ResultRepository resultRepository;
    private final UserService userService;
    private final QuestionService questionService;
    private final ModuleService moduleService;
    private final EntityMapper<User, UserDto> mapper;

    public ResultHandlerImpl(ResultRepository resultRepository,
                             UserService userService,
                             QuestionService questionService,
                             ModuleService moduleService,
                             UserMapperImpl mapper) {
        this.resultRepository = resultRepository;
        this.userService = userService;
        this.questionService = questionService;
        this.moduleService = moduleService;
        this.mapper = mapper;
    }

    @Override
    public void setResults(Map<Integer, String> results,
                           Authentication authentication) {

        for (Map.Entry<Integer, String> entry : results.entrySet()) {
            int questionId = entry.getKey();
            String selectedAnswer = entry.getValue();


            Result result = new Result();
            User user = new User();
            Optional<Question> question;
            Module module;

            if (authentication.isAuthenticated())
                user = userService.findByUsernameOrEmail(authentication.getName(), authentication.getName());

            question = questionService.findById(questionId);

            if (resultRepository.existsByUserIdAndQuestionId(user.getId(), questionId))
                break;

            if (question.isPresent()) {
                result.setUserId(user.getId());
                result.setQuestionId(question.get().getId());
                result.setAnswerIndex(question.get()
                        .getOptions()
                        .indexOf(selectedAnswer));

                module = moduleService.findByQuestion(question.get());

                result.setModuleId(module.getId());
            }

            resultRepository.save(result);
        }
    }

    //Поиск списка вопросов с выбранными вариантами по конретному модулю, для итогового отображения
    @Override
    public List<Question> findUserResultsByModule(long userId,
                                                  long moduleId) {
        List<Result> resultsList = resultRepository.findAll();

        List<Result> results = resultsList.stream()
                .filter(r -> r.getUserId() == userId && r.getModuleId() == moduleId).toList();

        return results.stream().map(r -> {
            Optional<Question> question = questionService.findById(r.getQuestionId());

            if (question.isPresent()) {
                long userAnswer = r.getAnswerIndex();
                question.get().setUserAnswer((int) userAnswer);
            }

            return question.orElse(null);
        }).toList();
    }


    //Подсчет результатов пользователей курса, по каждому модулю
    @Override
    public List<Course> countResults(List<Course> courseList,
                                     long userId) {
        List<Result> results = resultRepository.findAllResultsByUserId(userId);

        return courseList.stream().peek(course ->

                course.getModules().stream().peek(
                        module -> {

                            AtomicInteger countCorrectAnswer = new AtomicInteger();

                            results.forEach(result -> {

                                int sizeQuestions = 0;

                                if (result.getModuleId() == module.getId()) {
                                    List<Question> questions = questionService
                                            .findAllQuestionsByModuleID(module.getId());

                                    sizeQuestions = questions.size();

                                    questions.forEach(question -> {
                                        if (question.getCorrectAnswer() == result.getAnswerIndex())
                                            countCorrectAnswer.getAndIncrement();
                                    });
                                }

                                module.setUserResult(countCorrectAnswer + "/" + sizeQuestions);
                            });
                        }))
                .collect(Collectors.toList());
    }

    //Подсчет результа по конкретному модулю, и возврат списка людей по этому модуля
    @Override
    public List<UserDto> countResultsForUsersByModule(List<User> userList,
                                                      long moduleId) {
        List<Result> results = resultRepository.findAllByModuleId(moduleId);

        return userList.stream().map(user -> {

            UserDto dto = mapper.map(user);
            AtomicInteger countCorrectAnswers = new AtomicInteger();

            results.forEach(result -> {

                List<Question> questions = questionService
                        .findAllQuestionsByModuleID(result.getModuleId());
                int sizeQuestions = questions.size();

                if (result.getUserId() == user.getId()) {
                    questions.forEach(question -> {
                        if (question.getCorrectAnswer() == result.getAnswerIndex())
                            countCorrectAnswers.getAndIncrement();
                    });
                }

               dto.setResult(countCorrectAnswers+"/"+sizeQuestions);
            });

            countCorrectAnswers.set(0);
            return dto;
        }).collect(Collectors.toList());
    }
}
