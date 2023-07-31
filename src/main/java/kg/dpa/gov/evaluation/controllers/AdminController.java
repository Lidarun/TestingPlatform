package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.dto.UserDto;
import kg.dpa.gov.evaluation.models.enums.Role;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    private String showPage(Model model) {
        List<User> userList = userService.findAll();

        model.addAttribute("countUsers", userList.size());
        model.addAttribute("users", userList);
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);

        return "dashboard/dashboard";
    }

    @GetMapping("/{rolePage}")
    private String showFilterPage(@PathVariable("rolePage") Role role,
                                  Model model) {
        List<User> userList = userService.findAllByRole(role);

        model.addAttribute("countUsers", userList.size());
        model.addAttribute("users", userList);
        model.addAttribute("role", Role.ROLE_SUPER_ADMIN);

        return "dashboard/dashboard-filters/users-by-role";
    }

    @GetMapping("/update/{id}")
    private String updateUser(@PathVariable("id") long id,
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
    private String setRole(@PathVariable long id,
                           @RequestParam("role") Role role) {
        userService.changeRole(id, role);

        return "redirect:/dashboard";
    }

    @PostMapping("/delete/{id}")
    private String deleteUser(@PathVariable long id) {
        userService.deleteByID(id);
        return "redirect:/dashboard";
    }


    @ResponseBody
    @GetMapping("/search")
    public Optional<UserDto> searchUser(@RequestParam("query") String userInfo) {
        List<UserDto> users = userService.findAllAsUserDto();

        return users.stream().filter(user -> user.getEmail().toLowerCase().contains(userInfo.toLowerCase()) ||
                user.getFullName().toLowerCase().contains(userInfo.toLowerCase())).findFirst();

    }
}
