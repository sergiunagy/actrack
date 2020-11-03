package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.ProjectUserRoles;
import sena.activitytracker.acktrack.model.UserRoleKey;

public interface ProjectUserRolesRepository extends CrudRepository<ProjectUserRoles, Long> {

    public ProjectUserRoles findByUserRoleKey(UserRoleKey key);
}
