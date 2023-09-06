package kg.dpa.gov.evaluation.controllers;

import jakarta.validation.Valid;
import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.services.CourseService;
import kg.dpa.gov.evaluation.services.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/modules")
public class ModulesController {
    private final CourseService courseService;
    private final ModuleService moduleService;

    public ModulesController(CourseService courseService,
                             ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @GetMapping
    private String showPage(Model model) {
        List<Course> courses = courseService.findAll();

        model.addAttribute("formModule", new Module());
        model.addAttribute("courses", courses);
        model.addAttribute("modules", moduleService.findAll());

        return "dashboard/modules";
    }

    @GetMapping("/edit/{id}")
    private String editCourse(@PathVariable("id") long id,
                              Model model) {
        List<Course> courses = courseService.findAll();
        Module module = moduleService.findById(id);

        model.addAttribute("formModule", module);
        model.addAttribute("courses", courses);
        model.addAttribute("modules", moduleService.findAll());

        return "dashboard/edit-module";
    }

    @PostMapping
    private String createModule(@ModelAttribute("formModule") @Valid Module module,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAll());
            return "dashboard/modules";
        }

        moduleService.create(module);

        return "redirect:/modules";
    }

    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable("id") long id,
                               @ModelAttribute("formModule") Module updatedModule) {

        moduleService.update(id, updatedModule);

        return "redirect:/modules";
    }

    @PostMapping("/{id}")
    private String deleteCourse(@PathVariable("id") long id) {

        moduleService.deleteById(id);

        return "redirect:/modules";
    }
}
