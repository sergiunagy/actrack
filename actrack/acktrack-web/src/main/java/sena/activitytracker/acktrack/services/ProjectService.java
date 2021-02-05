package sena.activitytracker.acktrack.services;

import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.model.Project;

import java.util.Set;
import java.util.UUID;

public interface ProjectService extends CrudService<ProjectDTO, UUID> {

}
