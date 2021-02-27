package sena.activitytracker.acktrack.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    private Set<String> projectIds = new HashSet<>();
    private String userName;
    private String userUID; /*TODO: should this be output to the UI ?*/

    @Builder
    public ActivityDTO(Long id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String description,
                       LocalDate date, Duration duration, Boolean isExported, Set<String> workpackageIds,
                       Set<String> issueIds, Set<String> projectIds, String userName, String userUID) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.isExported = isExported;
        this.workpackageIds = workpackageIds;
        this.issueIds = issueIds;
        this.projectIds = projectIds;
        this.userName = userName;
        this.userUID = userUID;
    }
}
