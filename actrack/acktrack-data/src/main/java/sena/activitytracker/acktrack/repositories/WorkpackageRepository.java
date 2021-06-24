package sena.activitytracker.acktrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.Workpackage;

import java.util.UUID;

public interface WorkpackageRepository extends CrudRepository<Workpackage, Long> {
}
