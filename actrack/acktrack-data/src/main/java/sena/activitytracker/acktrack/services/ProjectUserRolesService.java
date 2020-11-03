package sena.activitytracker.acktrack.services;

import sena.activitytracker.acktrack.model.ProjectUserRoles;
import sena.activitytracker.acktrack.model.UserRoleKey;

public interface ProjectUserRolesService extends CrudService<ProjectUserRoles, Long>{

    public ProjectUserRoles findByUserRoleKey(UserRoleKey key);
}
