package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.enums.Role;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.repository.UserRepository;
import kg.dpa.gov.evaluation.services.UserService;
import kg.dpa.gov.evaluation.services.ValidationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService, ValidationService {
    private  final UserRepository userRep;

    public UserServiceImpl(UserRepository userRep) {
        this.userRep = userRep;
    }

    @Override
    public void save(User user) {
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        user.setRole(Set.of(Role.ROLE_USER));
        userRep.save(user);
    }

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
        System.out.println(error);
        return error;
    }

    @Override
    public ObjectError existUserByUsername(String username) {
        ObjectError error = null;
        if (userRep.existsUserByUsername(username)) error =
                new ObjectError("global", "Username '"+username+"' занят");

        System.out.println(error.toString());
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
