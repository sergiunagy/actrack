package sena.activitytracker.acktrack.mappers;

import org.mapstruct.Mapper;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.model.Project;

@Mapper
public interface ProjectMapper {

    ProjectDTO toProjectDTO(Project project);
    Project toProject(ProjectDTO projectDTO);
}
