package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.repositories.ProjectRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Set<Project> findAll() {
        Set<Project> projects = new HashSet<>();
        projectRepository.findAll().forEach(projects::add);

        return projects;
    }

    @Override
    public Optional<Project> findById(UUID id) {

        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project) {

        return projectRepository.save(project);
    }

    @Override
    public Set<Project> saveAll(Set<Project> projects) {
        Set<Project> retProjects = new HashSet<>();
        projectRepository.saveAll(projects).forEach(retProjects::add);

        return retProjects;
    }

    @Override
    public void delete(Project project) {

        projectRepository.delete(project);
    }

    @Override
    public void deleteById(UUID id) {
        projectRepository.deleteById(id);
    }
}
