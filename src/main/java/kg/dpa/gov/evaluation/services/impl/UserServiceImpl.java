package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.mappers.EntityMapper;
import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.models.dto.UserDto;
import kg.dpa.gov.evaluation.models.enums.Role;
import kg.dpa.gov.evaluation.repository.UserRepository;
import kg.dpa.gov.evaluation.services.CourseService;
import kg.dpa.gov.evaluation.services.UserService;
import kg.dpa.gov.evaluation.services.ValidationService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"users"})
public class UserServiceImpl implements UserService, ValidationService {

    private final UserRepository userRep;
    private final CourseService courseService;
    private final EntityMapper<User, UserDto> mapper;

    public UserServiceImpl(UserRepository userRep,
                           CourseService courseService,
                           EntityMapper<User, UserDto> mapper) {
        this.userRep = userRep;
        this.courseService = courseService;
        this.mapper = mapper;
    }


    @Override
    public void create(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        user.setRole(Set.of(Role.ROLE_USER));
        userRep.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRep.findAll();
    }

    @Override
    public void changeRole(long id, Role role) {

        Optional<User> user = userRep.findById(id);

        if (user.isPresent()) {

            if (user.get().getRole().contains(role))
                user.get().getRole().remove(role);

            else
                user.get().getRole().add(role);

            userRep.save(user.get());
        }
    }

    @Override
    public void deleteByID(long id) {
        userRep.deleteById(id);
    }

    @Override
    public User findById(long id) {
        return userRep.findById(id).orElse(null);
    }

    @Override
    public User findByUsernameOrEmail(String username,
                                      String email) {
        return userRep.findByUsernameOrEmail(username, email).orElse(null);
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRep.findAllByRoleContains(role);
    }

    @Override
    public List<User> findAllByRoleAndCourse(Role role) {
        return null;
    }


    @Override
    public void addCourse(String username, long courseID) {
        Optional<User> userOptional = userRep.findByUsernameOrEmail(username, username);
        Course course = courseService.findById(courseID);

        if (userOptional.isPresent() && course != null) {
            User user = userOptional.get();
            user.getCourses().add(course);
            userRep.save(user);
        }
    }

    @Override
    public List<User> findAllByCourseId(long courseId) {
        Course course = courseService.findById(courseId);
        return userRep.findAllByCourses(course);
    }

    @Override
    @Cacheable
    public List<UserDto> findAllAsUserDto() {
        List<User> users = userRep.findAll();
        return users.stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    @CachePut
    public List<UserDto> updateCache() {
        List<User> users = userRep.findAll();
        return users.stream().map(mapper::map).collect(Collectors.toList());
    }


    //    VALIDATION
    @Override
    public ObjectError comparePassword(String password, String confirmPassword) {
        ObjectError error = null;
        if (!password.equals(confirmPassword)) error =
                new ObjectError("global", "Пароли не совпадают, попробуйте снова");

        return error;
    }

    @Override
    public ObjectError existUserByEmail(String email) {
        ObjectError error = null;
        if (userRep.existsByEmail(email)) error =
                new ObjectError("global", "Пользователь с такой почтой существует");

        return error;
    }

    @Override
    public ObjectError existUserByUsername(String username) {
        ObjectError error = null;
        if (userRep.existsUserByUsername(username)) error =
                new ObjectError("global", "Username '"+username+"' занят");

        return error;
    }

    @Override
    public ObjectError validPassword(String password) {
        if (password.isEmpty()) return null;

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches())
            return new ObjectError("global",
                    "Пароль должен содержать не менее 8-ми символов, " +
                            "\nв том числе цифры, прописные и строчные буквы");

        return null;
    }

}
