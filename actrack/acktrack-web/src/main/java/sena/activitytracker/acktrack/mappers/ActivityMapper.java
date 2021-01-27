package sena.activitytracker.acktrack.mappers;

import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.model.Activity;

public interface ActivityMapper {

    ActivityDTO toActivityDTO(Activity activity);
    Activity toActivity(ActivityDTO activityDTO);

}
