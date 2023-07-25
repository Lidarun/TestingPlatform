package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.Result;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.repository.ResultRepository;
import kg.dpa.gov.evaluation.services.ModuleService;
import kg.dpa.gov.evaluation.services.QuestionService;
import kg.dpa.gov.evaluation.services.ResultHandler;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ResultHandlerImpl implements ResultHandler {

    private final ResultRepository resultRepository;
    private final UserService userService;
    private final QuestionService questionService;
    private final ModuleService moduleService;

    public ResultHandlerImpl(ResultRepository resultRepository, UserService userService, QuestionService questionService, ModuleService moduleService) {
        this.resultRepository = resultRepository;
        this.userService = userService;
        this.questionService = questionService;
        this.moduleService = moduleService;
    }

    @Override
    public void setResults(Map<Integer, String> results, Authentication authentication) {

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
            System.out.println(question);

            if (question.isPresent()) {
                result.setUser_id(user.getId());
                result.setQuestion_id(question.get().getId());
                result.setAnswer_index(question.get()
                                                .getOptions()
                                                .indexOf(selectedAnswer));

                module = moduleService.findByQuestion(question.get());

                result.setModule_id(module.getId());
            }

            resultRepository.save(result);
        }
    }

}
