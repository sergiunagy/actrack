package sena.activitytracker.acktrack.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ActivityDTO extends BaseEntityDto{

    private String description;
    private LocalDate date;
    private Duration duration;
    private Boolean isExported;

    /* Properties requiring assembly*/
    private Set<String> workpackageIds = new HashSet<>();
    private Set<String> issueIds = new HashSet<>();
    private Set<String> projectId = new HashSet<>();
    private User userName;
    private User userUID;

}
