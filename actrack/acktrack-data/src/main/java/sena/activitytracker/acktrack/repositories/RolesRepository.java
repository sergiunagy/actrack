package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.Role;

public interface RolesRepository extends CrudRepository<Role, Long> {
}
