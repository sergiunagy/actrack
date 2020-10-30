package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
