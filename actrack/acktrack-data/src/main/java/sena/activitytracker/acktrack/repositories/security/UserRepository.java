package sena.activitytracker.acktrack.repositories.security;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.security.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
}
