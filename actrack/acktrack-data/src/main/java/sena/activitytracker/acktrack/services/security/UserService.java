package sena.activitytracker.acktrack.services.security;

import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.services.CrudService;

import java.util.UUID;

public interface UserService extends CrudService<User, UUID> {
}
