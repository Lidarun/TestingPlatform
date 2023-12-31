package kg.dpa.gov.evaluation.controllers;

import jakarta.validation.Valid;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.services.CourseService;
import kg.dpa.gov.evaluation.services.ModuleService;
import kg.dpa.gov.evaluation.services.QuestionService;
import kg.dpa.gov.evaluation.services.QuestionValidationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    private final QuestionService questionService;
    private final QuestionValidationService service;
    private final CourseService courseService;
    private final ModuleService moduleService;

    public QuestionsController(QuestionService questionService,
                               QuestionValidationService service,
                               CourseService courseService,
                               ModuleService moduleService) {
        this.questionService = questionService;
        this.service = service;
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @GetMapping("/{pageNum}")
    public String showPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum) {
        model.addAttribute("formQuestion", new Question());
        model.addAttribute("varA", new String());
        model.addAttribute("varB", new String());
        model.addAttribute("varC", new String());
        model.addAttribute("varD", new String());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("modules", moduleService.findAll());

        Page<Question> page = questionService.getItems(PageRequest.of(pageNum, 10));

        List<Question> list = page.getContent();
        model.addAttribute("listQuestions", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        return "dashboard/question";
    }

//   Возврщаетя список модулей курсаа в медод AJAX
    @ResponseBody
    @GetMapping("/loadModules")
    public ResponseEntity<List<Module>> loadModulesForCourse(@RequestParam Long courseId) {
        List<Module> modules = moduleService.getModulesByCourseId(courseId);
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    @PostMapping("/{pageNum}")
    public String createQuestion(@PathVariable("pageNum") int pageNum,
                              @ModelAttribute("formQuestion") @Valid Question question,
                              BindingResult result, Model model) {
        Page<Question> page = questionService.getItems(PageRequest.of(pageNum, 5));

        List<Question> list = page.getContent();
        model.addAttribute("listQuestions", list);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("modules", moduleService.findAll());
        model.addAttribute("formQuestion", question);

        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("modules", moduleService.findAll());

        System.out.println(question);

        ObjectError error = service.checkFields(question);
        if (error != null)
            result.addError(error);

        if (result.hasErrors())
            return "dashboard/question";

        questionService.create(question);

        return "redirect:/questions/" + pageNum;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model,
                               @ModelAttribute("varA") String a,
                               @ModelAttribute("varB") String b,
                               @ModelAttribute("varC") String c,
                               @ModelAttribute("varD") String d) {
        Page<Question> page = questionService.getItems(PageRequest.of(0, 5));

        List<Question> list = page.getContent();
        model.addAttribute("listQuestions", list);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("modules", moduleService.findAll());

        Optional<Question> questionOptional = questionService.findById(id);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            model.addAttribute("formQuestion", question);
            question.getOptions().add(a);
            question.getOptions().add(b);
            question.getOptions().add(c);
            question.getOptions().add(d);

            return "dashboard/edit-question";

        } else
            return "redirect:/questions/0";
    }


    @PostMapping("/edit/{id}")
    public String updateQuestion(@PathVariable("id") int id,
                                 @ModelAttribute("formQuestion") Question updatedQuestion) {
        Optional<Question> questionOptional = questionService.findById(id);
        if (questionOptional.isPresent())
            questionService.create(updatedQuestion);

        return "redirect:/questions/0";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") int id) {
        questionService.deleteById(id);
        return "redirect:/questions/0";
    }
}
