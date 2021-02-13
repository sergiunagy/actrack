package sena.activitytracker.acktrack.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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

    @Builder
    public WorkpackageDTO(String name, String description, LocalDate startDate, LocalDate endDate, Set<String> activityIds, Set<String> issueIds, Set<String> userIds, Set<String> ues, int hoursBooked) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityIds = activityIds;
        this.issueIds = issueIds;
        this.userIds = userIds;
        this.ues = ues;
        this.hoursBooked = hoursBooked;
    }

    public int getNoOfActivities(){

        return activityIds.size();
    }

}
