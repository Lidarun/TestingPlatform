package kg.dpa.gov.evaluation.services;

import kg.dpa.gov.evaluation.models.Module;

import java.util.List;

public interface ModuleService {
    void create(Module module);

    List<Module> findAll();

    void deleteById(long id);

    Module findById(long id);

    void update(long id, Module updatedModule);

    List<Module> getModulesByCourseId(Long courseId);}
