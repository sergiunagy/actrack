package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.ProjectUserRole;
import sena.activitytracker.acktrack.repositories.ProjectUserRolesRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectUserRolesServiceImpl implements ProjectUserRolesService {

    private final ProjectUserRolesRepository projectUserRolesRepository;

    @Override
    public Set<ProjectUserRole> findAll() {
        Set<ProjectUserRole> issues = new HashSet<>();
        projectUserRolesRepository.findAll().forEach(issues::add);

        return issues;
    }

    @Override
    public ProjectUserRole findById(UUID id) {

        return projectUserRolesRepository.findById(id).orElse(null);
    }

    @Override
    public ProjectUserRole save(ProjectUserRole issue) {

        return projectUserRolesRepository.save(issue);
    }

    @Override
    public Set<ProjectUserRole> saveAll(Set<ProjectUserRole> issues) {
        Set<ProjectUserRole> retProjectUserRolesses = new HashSet<>();
        projectUserRolesRepository.saveAll(issues).forEach(retProjectUserRolesses::add);

        return retProjectUserRolesses;
    }

    @Override
    public void delete(ProjectUserRole issue) {

        projectUserRolesRepository.delete(issue);
    }

    @Override
    public void deleteById(UUID id) {
        projectUserRolesRepository.deleteById(id);
    }


}
