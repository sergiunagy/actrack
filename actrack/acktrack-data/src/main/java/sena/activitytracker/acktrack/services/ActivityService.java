package sena.activitytracker.acktrack.services;

import sena.activitytracker.acktrack.model.Activity;

import java.time.LocalDate;
import java.util.Set;

public interface ActivityService extends CrudService<Activity,Long> {

    Set<Activity> findAllActivitiesBetweenDates(LocalDate startDate, LocalDate endDate);
}
