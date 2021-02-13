package sena.activitytracker.acktrack.services;

import sena.activitytracker.acktrack.dtos.WorkpackageDTO;
import sena.activitytracker.acktrack.model.Workpackage;

import java.util.UUID;

public interface WorkpackageService extends CrudService<WorkpackageDTO, UUID> {
}
