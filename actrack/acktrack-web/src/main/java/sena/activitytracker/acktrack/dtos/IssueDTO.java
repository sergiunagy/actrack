package sena.activitytracker.acktrack.dtos;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.Workpackage;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class IssueDTO extends BaseEntityDto implements Comparable<IssueDTO>{

    private String issue_id;

    private String shortName;

    private String description;

    private String link;

    private LocalDate createdDate;

    private LocalDate closedDate;

    private String projectName;

    private int noOfActivities;

    private int noOfWorkpackages;

    private int hoursWorked;

    @Builder
    public IssueDTO(@NonNull String issue_id, String shortName, String description, String link, @NonNull LocalDate createdDate, LocalDate closedDate, String projectName, int noOfActivities, int noOfWorkpackages, int hoursWorked) {
        this.issue_id = issue_id;
        this.shortName = shortName;
        this.description = description;
        this.link = link;
        this.createdDate = createdDate;
        this.closedDate = closedDate;
        this.projectName = projectName;
        this.noOfActivities = noOfActivities;
        this.noOfWorkpackages = noOfWorkpackages;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public int compareTo(IssueDTO compareIssue) {
        return this.createdDate.compareTo(compareIssue.createdDate);
    }

    public String isClosed(){

        return (this.closedDate!=null)? "Closed on: "+ this.closedDate.toString(): "OPEN";
    }
}
