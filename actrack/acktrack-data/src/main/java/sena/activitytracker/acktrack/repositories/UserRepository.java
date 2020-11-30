package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.security.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
}
