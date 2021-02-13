package sena.activitytracker.acktrack.dtos;

import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WorkpackageDTO extends BaseEntityDto{

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<String> activityIds = new HashSet<>();
    private Set<String> issueIds = new HashSet<>();
    private Set<String> userIds = new HashSet<>();
    private Set<String> ues = new HashSet<>();

    private int hoursBooked;

    public int getNoOfActivities(){

        return activityIds.size();
    }

}
