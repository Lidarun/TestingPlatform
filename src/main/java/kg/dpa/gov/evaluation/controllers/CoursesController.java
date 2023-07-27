package kg.dpa.gov.evaluation.controllers;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private final CourseService service;

    public CoursesController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    private String showPage(Model model) {
        List<Course> courseList = service.findAll();

        model.addAttribute("formCourse", new Course());
        model.addAttribute("courses", courseList);

        return "dashboard/course";
    }

    @GetMapping("/edit/{id}")
    private String editCourse(@PathVariable("id") long id,
                              Model model) {
        List<Course> courseList = service.findAll();
        Course course = service.findById(id);

        model.addAttribute("courses", courseList);

        if (course != null) {
            model.addAttribute("formCourse", course);
            return "dashboard/edit-course";

        }else
            return "redirect:/courses";
    }

    @PostMapping
    private String createCourse(@ModelAttribute("formCourse") Course course,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty())
            try {
                String fileName = System.currentTimeMillis() + "-"
                        + imageFile.getOriginalFilename();

//                String uploadDir = System.getProperty("user.home")
//                        + "/Desktop/dpa-training";
                String uploadDir = "uploads";

                Path dirPath = Paths.get(uploadDir);
                if (!Files.exists(dirPath))
                    Files.createDirectories(dirPath);

                Path filePath = Paths.get(uploadDir, fileName);

                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                course.setPathImage(filePath.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        service.create(course);

        return "redirect:/courses";
    }

    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable("id") long id,
                                 @ModelAttribute("formCourse") Course updatedCourse) {
        service.update(id, updatedCourse);

        return "redirect:/courses";
    }

    @PostMapping("/{id}")
    private String deleteCourse(@PathVariable("id") long id) {
        service.deleteById(id);
        return "redirect:/courses";
    }
}
