package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String showPage(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("countUsers", userList.size());
//        model.addAttribute("users", userList);
        return "dashboard";
    }
}
