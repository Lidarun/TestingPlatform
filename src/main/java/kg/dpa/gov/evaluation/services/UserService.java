package kg.dpa.gov.evaluation.services;


import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.models.dto.UserDto;
import kg.dpa.gov.evaluation.models.enums.Role;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

public interface UserService {

    void create(User user);

    List<User> findAll();

    void changeRole(long id, Role role);

    void deleteByID(long id);

    User findById(long id);

    User findByUsernameOrEmail(String username, String email);

    List<User> findAllByRole(Role role);

    List<User> findAllByRoleAndCourse(Role role);

    void addCourse(String username, long course);

    List<User> findAllByCourseId(long courseId);

    List<UserDto> findAllAsUserDto();

    @CachePut
    List<UserDto> updateCache();
}
