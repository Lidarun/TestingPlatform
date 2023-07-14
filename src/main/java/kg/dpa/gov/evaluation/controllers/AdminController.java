package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.enums.Role;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String showPage(Model model) {
        List<User> userList = userService.findAll();

        model.addAttribute("countUsers", userList.size());
        model.addAttribute("users", userList);
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);

        return "dashboard/dashboard";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             Model model) {
        User user = userService.findById(id);

        model.addAttribute("fromUser", user);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);
        model.addAttribute("roleA", Role.ROLE_SUPER_ADMIN);
        model.addAttribute("roles", Role.values());

        return "dashboard/edit-user";
    }

    @PostMapping("/update/{id}")
    public String setRole(@PathVariable("id") long id,
                          @RequestParam("role") Role role) {
        boolean res = userService.changeRole(id, role);

        System.out.println(role.getName());

        return "redirect:/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteByID(id);
        return "redirect:/dashboard";
    }
}
