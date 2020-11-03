package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.Role;
import sena.activitytracker.acktrack.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> findAll() {
        Set<Role> issues = new HashSet<>();
        roleRepository.findAll().forEach(issues::add);

        return issues;
    }

    @Override
    public Role findById(Long id) {

        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role save(Role issue) {

        return roleRepository.save(issue);
    }

    @Override
    public Set<Role> saveAll(Set<Role> issues) {
        Set<Role> retRoles = new HashSet<>();
        roleRepository.saveAll(issues).forEach(retRoles::add);

        return retRoles;
    }

    @Override
    public void delete(Role issue) {

        roleRepository.delete(issue);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
