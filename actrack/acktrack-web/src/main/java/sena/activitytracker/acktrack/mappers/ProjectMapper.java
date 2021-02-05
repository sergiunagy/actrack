package sena.activitytracker.acktrack.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.model.Project;

@Mapper
@DecoratedWith(ProjectMapperDecorator.class)
public interface ProjectMapper {

    ProjectDTO toProjectDTO(Project project);
    Project toProject(ProjectDTO projectDTO);
}
