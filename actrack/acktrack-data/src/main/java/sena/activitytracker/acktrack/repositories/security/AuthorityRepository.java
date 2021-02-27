package sena.activitytracker.acktrack.repositories.security;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.security.Authority;

import java.util.UUID;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
