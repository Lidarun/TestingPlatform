package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.User;
import org.springframework.validation.ObjectError;

public interface UserService {
    void save(User user);
}
