package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.Project;

import java.util.Set;
import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Set<Project> findAll();
}
