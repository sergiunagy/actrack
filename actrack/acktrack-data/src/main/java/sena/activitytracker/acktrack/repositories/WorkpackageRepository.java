package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.Workpackage;

public interface WorkpackageRepository extends CrudRepository<Workpackage, Long> {
}
