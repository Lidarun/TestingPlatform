package kg.dpa.gov.evaluation.controllers;

import jakarta.validation.Valid;
import kg.dpa.gov.evaluation.enums.Language;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import kg.dpa.gov.evaluation.services.QuestionValidationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionValidationService service;

    public QuestionController(QuestionRepository questionRepository, QuestionValidationService service) {
        this.questionRepository = questionRepository;
        this.service = service;
    }

    @GetMapping()
    public String showPage(Model model) {
        model.addAttribute("formQuestion", new Question());
        model.addAttribute("varA", new String());
        model.addAttribute("varB", new String());
        model.addAttribute("varC", new String());
        model.addAttribute("varD", new String());
        model.addAttribute("lang", Language.values());

        List<Question> list = questionRepository.findAll();
        model.addAttribute("listQuestions", list);
        return "formQuestion";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model,
                               @ModelAttribute("varA") String a,
                               @ModelAttribute("varB") String b,
                               @ModelAttribute("varC") String c,
                               @ModelAttribute("varD") String d) {
        List<Question> list = questionRepository.findAll();
        model.addAttribute("listQuestions", list);

        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            model.addAttribute("formQuestion", question);
            question.getOptions().add(a);
            question.getOptions().add(b);
            question.getOptions().add(c);
            question.getOptions().add(d);

            return "edit-question";

        } else return "redirect:/questions";

    }

    @PostMapping()
    public String newQuestion(@ModelAttribute("formQuestion") @Valid Question question,
                              BindingResult result, Model model) {
        model.addAttribute(question);
        List<Question> list = questionRepository.findAll();
        model.addAttribute("listQuestions", list);
        ObjectError error = service.checkFields(question);
        if (error != null) result.addError(error);

        if (result.hasErrors()) return "formQuestion";

        questionRepository.save(question);
        return "redirect:/questions";
    }

    @PostMapping("/edit/{id}")
    public String updateQuestion(@PathVariable("id") int id,
                                 @ModelAttribute("formQuestion") Question updatedQuestion) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isPresent())
            questionRepository.save(updatedQuestion);

        return "redirect:/questions";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionRepository.deleteById(id);
        return "redirect:/questions";
    }
}
