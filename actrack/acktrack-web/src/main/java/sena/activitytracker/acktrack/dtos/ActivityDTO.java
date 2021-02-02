package sena.activitytracker.acktrack.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sena.activitytracker.acktrack.model.Activity;
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
public class ActivityDTO extends BaseEntityDto implements Comparable<ActivityDTO>{

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

    @Override
    public int compareTo(ActivityDTO comparedActivity) {

        return date.compareTo(comparedActivity.date);
    }
}
