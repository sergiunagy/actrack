package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.ProjectUserRole;

import java.util.UUID;

public interface ProjectUserRolesRepository extends CrudRepository<ProjectUserRole, UUID> {

}
