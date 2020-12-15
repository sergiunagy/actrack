package sena.activitytracker.acktrack.services.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.repositories.security.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> findAll() {
        Set<Role> roles = new HashSet<>();
        roleRepository.findAll().forEach(roles::add);

        return roles;
    }

    @Override
    public Optional<Role> findById(UUID id) {

        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {

        return roleRepository.save(role);
    }

    @Override
    public Set<Role> saveAll(Set<Role> roles) {
        Set<Role> retRoles = new HashSet<>();
        roleRepository.saveAll(roles).forEach(retRoles::add);

        return retRoles;
    }

    @Override
    public void delete(Role issue) {

        roleRepository.delete(issue);
    }

    @Override
    public void deleteById(UUID id) {
        roleRepository.deleteById(id);
    }
}
