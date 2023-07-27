package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.models.Result;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface ResultHandler {

    void setResults(Map<Integer, String> results, Authentication authentication);

    List<Question> findAllResultsByUserIdAndModuleId(long userId, long moduleId);

    List<Result> findAllResultsByUserId(long id);

    List<Course> countResults(List<Course> courseList, long userId);
}
