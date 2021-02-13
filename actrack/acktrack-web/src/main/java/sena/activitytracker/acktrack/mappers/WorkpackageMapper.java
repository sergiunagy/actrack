package sena.activitytracker.acktrack.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.dtos.WorkpackageDTO;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.Workpackage;

@Mapper
@DecoratedWith(WorkpackageMapperDecorator.class)
public interface WorkpackageMapper {

    WorkpackageDTO toWorkpackageDTO(Workpackage workpackage);
    Workpackage toWorkpackage(WorkpackageDTO workpackageDTO);
}
