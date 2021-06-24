package sena.activitytracker.acktrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.Activity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

    Set<Activity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    Set<Activity> findAllByDate(LocalDate date);

}
