package kg.dpa.gov.evaluation.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class LanguageController {

//    @GetMapping("/changeLanguage")
//    public String changeLanguage(@RequestParam("lang") String lang,
//                                 HttpServletRequest request,
//                                 HttpServletResponse response) {
//        Locale locale = new Locale(lang);
//        request.getSession().setAttribute("locale", locale);
//        return "redirect:/";
//    }
}