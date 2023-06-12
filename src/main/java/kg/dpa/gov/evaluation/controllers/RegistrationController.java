package kg.dpa.gov.evaluation.controllers;

import jakarta.validation.Valid;
import kg.dpa.gov.evaluation.enums.Role;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showPage(Model model){
        model.addAttribute("userForm", new User());
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userForm") @Valid User user,
                               BindingResult bindingResult , Model model){
        model.addAttribute(user);

//        ObjectError errorEmail = employeeService.existEmployee(employee.getEmail());
//        if (errorEmail != null) bindingResult.addError(errorEmail);
//
        ObjectError errorConfirmPassword = userService
                .comparePassword(user.getPassword(), user.getConfirmPassword());
        if (errorConfirmPassword != null) bindingResult.addError(errorConfirmPassword);
//
//        ObjectError errorPassword = employeeService.validPassword(employee.getPassword());
//        if (errorPassword != null) bindingResult.addError(errorPassword);

        if(bindingResult.hasErrors()) return "register";

        userService.save(user);
        return "redirect:/login";
    }
}
