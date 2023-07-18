package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.enums.Role;
import kg.dpa.gov.evaluation.models.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> findAll();
    boolean changeRole(long id, Role role);
    void deleteByID(long id);

    User findById(long id);

    List<User> findAllByRole(Role role);

    List<User> findAllByRoleAndCourse(Role role);

    boolean coursePresent();
}
