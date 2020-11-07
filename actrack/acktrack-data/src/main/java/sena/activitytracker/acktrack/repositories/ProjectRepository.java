package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
