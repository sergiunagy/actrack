package sena.activitytracker.acktrack.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.model.Activity;

@Mapper
@DecoratedWith(ActivityMapperDecorator.class)
public interface ActivityMapper {

    ActivityDTO toActivityDTO(Activity activity);
    Activity toActivity(ActivityDTO activityDTO);

}
