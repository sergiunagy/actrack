package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.ProjectUserRoles;
import sena.activitytracker.acktrack.model.UserRoleKey;
import sena.activitytracker.acktrack.repositories.ProjectUserRolesRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectUserRolesServiceImpl implements ProjectUserRolesService {

    private final ProjectUserRolesRepository projectUserRolesRepository;

    @Override
    public Set<ProjectUserRoles> findAll() {
        Set<ProjectUserRoles> issues = new HashSet<>();
        projectUserRolesRepository.findAll().forEach(issues::add);

        return issues;
    }

    @Override
    public ProjectUserRoles findById(Long id) {

        return projectUserRolesRepository.findById(id).orElse(null);
    }

    @Override
    public ProjectUserRoles save(ProjectUserRoles issue) {

        return projectUserRolesRepository.save(issue);
    }

    @Override
    public Set<ProjectUserRoles> saveAll(Set<ProjectUserRoles> issues) {
        Set<ProjectUserRoles> retProjectUserRoless = new HashSet<>();
        projectUserRolesRepository.saveAll(issues).forEach(retProjectUserRoless::add);

        return retProjectUserRoless;
    }

    @Override
    public void delete(ProjectUserRoles issue) {

        projectUserRolesRepository.delete(issue);
    }

    @Override
    public void deleteById(Long id) {
        projectUserRolesRepository.deleteById(id);
    }

    @Override
    public ProjectUserRoles findByUserRoleKey(UserRoleKey key) {

        return projectUserRolesRepository.findByUserRoleKey(key);
    }
}
