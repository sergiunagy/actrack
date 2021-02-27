package sena.activitytracker.acktrack.services.security;

import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.services.CrudService;

import java.util.Optional;
import java.util.UUID;

public interface RoleService extends CrudService<Role, Long> {

    public Role save(Role role) ;

    public Optional<Role> findById(Long uuid);
}
