package kg.dpa.gov.evaluation.services.impl;

import kg.dpa.gov.evaluation.models.Course;
import kg.dpa.gov.evaluation.models.Module;
import kg.dpa.gov.evaluation.models.Question;
import kg.dpa.gov.evaluation.repository.ModuleRepository;
import kg.dpa.gov.evaluation.services.CourseService;
import kg.dpa.gov.evaluation.services.ModuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRep;
    private final CourseService courseService;

    public ModuleServiceImpl(ModuleRepository moduleRep, CourseService courseService) {
        this.moduleRep = moduleRep;
        this.courseService = courseService;
    }


    @Override
    public void create(Module module) {
        Course course = module.getCourse();
        course.addModule(module);

        moduleRep.save(module);
        courseService.update(course.getId(), course);
    }

    @Override
    public List<Module> findAll() {
        return moduleRep.findAll();
    }

    @Override
    public void deleteById(long id) {
        moduleRep.deleteById(id);
    }

    @Override
    public Module findById(long id) {
        Optional<Module> module = moduleRep.findById(id);

        return module.orElse(null);

    }

    @Override
    public void update(long id, Module updatedModule) {
        if (moduleRep.findById(id).isPresent())
            moduleRep.save(updatedModule);
    }

    @Override
    public List<Module> getModulesByCourseId(Long courseId) {
        return moduleRep.findAllByCourse(courseService.findById(courseId));
    }

    @Override
    public Module findByQuestion(Question question) {
        return moduleRep.findByQuestion(question);
    }
}
