package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
