package kg.dpa.gov.evaluation.controllers;

import jakarta.validation.Valid;
import kg.dpa.gov.evaluation.models.User;
import kg.dpa.gov.evaluation.services.UserService;
import kg.dpa.gov.evaluation.services.ValidationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ValidationService validationService;

    public RegistrationController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @GetMapping()
    public String showPage(Model model){
        model.addAttribute("userForm", new User());
        return "pages/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userForm") @Valid User user,
                               BindingResult bindingResult , Model model){
        model.addAttribute(user);

        ObjectError errorUsername = validationService.existUserByUsername(user.getUsername());
        if (errorUsername != null) bindingResult.addError(errorUsername);

        ObjectError errorEmail = validationService.existUserByEmail(user.getEmail());
        if (errorEmail != null) bindingResult.addError(errorEmail);

        ObjectError errorConfirmPassword = validationService
                .comparePassword(user.getPassword(), user.getConfirmPassword());
        if (errorConfirmPassword != null) bindingResult.addError(errorConfirmPassword);

//        ObjectError errorPassword = validationService.validPassword(user.getPassword());
//        if (errorPassword != null) bindingResult.addError(errorPassword);

        if(bindingResult.hasErrors()) return "pages/register";

        userService.save(user);
        return "redirect:/login";
    }
}
