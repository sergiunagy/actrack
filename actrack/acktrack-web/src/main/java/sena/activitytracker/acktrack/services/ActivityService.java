package sena.activitytracker.acktrack.services;

import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.model.Activity;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ActivityService extends CrudService<Activity, Long> {

    Set<Activity> findAllActivitiesBetweenDates(LocalDate startDate, LocalDate endDate);
    Set<ActivityDTO> listAllActivities();

}
