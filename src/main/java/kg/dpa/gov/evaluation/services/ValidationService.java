package kg.dpa.gov.evaluation.services;

import org.springframework.validation.ObjectError;

public interface ValidationService {
    ObjectError comparePassword(String password, String confirmPassword);
    ObjectError existUserByEmail(String email);
    ObjectError existUserByUsername(String username);
    ObjectError validPassword(String password);
}
