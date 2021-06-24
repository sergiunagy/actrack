package sena.activitytracker.acktrack.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.security.Role;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
