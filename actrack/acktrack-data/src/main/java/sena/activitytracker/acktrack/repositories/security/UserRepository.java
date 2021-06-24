package sena.activitytracker.acktrack.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.security.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {

    /* security : provide user search by username*/
    Optional<User> findByUsername(String username);
}
