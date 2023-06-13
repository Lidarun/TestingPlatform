package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.QuestionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class QuestionController {
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping()
    public String showPage(Model model) {
        model.addAttribute("formQuestion", new Question());
        model.addAttribute("varA", new String());
        model.addAttribute("varB", new String());
        model.addAttribute("varC", new String());
        model.addAttribute("varD", new String());

        List<Question> list = questionRepository.findAll();
        model.addAttribute("listQuestions", list);
        return "formQuestion";
    }

    @PostMapping()
    public String newQuestion(@ModelAttribute("formQuestion") Question question) {
        System.out.println("OPT" + question.getOptions());
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model,
                               @ModelAttribute("varA") String a,
                               @ModelAttribute("varB") String b,
                               @ModelAttribute("varC") String c,
                               @ModelAttribute("varD") String d) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            model.addAttribute("formQuestion", question);
            question.getOptions().add(a);
            question.getOptions().add(b);
            question.getOptions().add(c);
            question.getOptions().add(d);
            return "edit-question";

        } else {
            return "redirect:/questions";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateQuestion(@PathVariable("id") int id,
                                 @ModelAttribute("question") Question updatedQuestion) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            question.setQuestion(updatedQuestion.getQuestion());

            questionRepository.save(question);
        }
        return "redirect:/questions";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionRepository.deleteById(id);
        return "redirect:/questions";
    }
}
