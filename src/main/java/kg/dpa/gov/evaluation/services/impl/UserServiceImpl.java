package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.enums.Role;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.repository.UserRepository;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
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
}
