package sena.activitytracker.acktrack.services.security;

import sena.activitytracker.acktrack.dtos.UserDTO;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.services.CrudService;

import java.util.UUID;

public interface UserService extends CrudService<UserDTO, Long> {


}
