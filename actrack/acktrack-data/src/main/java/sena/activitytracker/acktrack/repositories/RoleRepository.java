package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.security.Role;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
}
