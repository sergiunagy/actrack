package sena.activitytracker.acktrack.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue extends BaseEntity {

    @Column(name = "short_description")
    private String shortName;

    @NaturalId
    @Column(name = "issue_tracking_id")
    private String issue_id;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "link_to_issue")
    private String link;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "closed_date")
    private LocalDate closedDate;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "issues_workpackages",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "workpackage_id"))
    private Set<Workpackage> workpackages = new HashSet<>() ;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "issues_activities",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activities = new HashSet<>() ;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Issue(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String shortName, String issue_id, String description, String link, LocalDate createdDate, LocalDate closedDate, Set<Workpackage> workpackages, Set<Activity> activities, Project project) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.shortName = shortName;
        this.issue_id = issue_id;
        this.description = description;
        this.link = link;
        this.createdDate = createdDate;
        this.closedDate = closedDate;
        /* Only overwrite with non-null values */
        if(workpackages != null) this.workpackages = workpackages;
        if(activities != null) this.activities = activities;
        this.project = project;
    }

    /*Helper methods*/

    public Workpackage addWorkpackage(Workpackage workpackage) {

        if (workpackage == null)
            throw new RuntimeException("Null workpackage passed to addIssue for Project id:" + this.getId());

        workpackage.getIssues().add(this);
        this.workpackages.add(workpackage);

        return workpackage;
    }

    public Set<Workpackage> addWorkpackages(Set<Workpackage> workpackages) {

        if (workpackages == null) /* Empty Set means nothing will execute in the forEach, so no need to check*/
            throw new RuntimeException("Null workpackages list passed for Project id:" + this.getId());

        workpackages.forEach(this::addWorkpackage);

        return workpackages;
    }
    public Activity addActivity(Activity activity) {

        /* Null checks */
        if (activity == null || activity.getIssues()==null)
            throw new RuntimeException("Null activity passed to addIssue for Project id:" + this.getId());

        /* Append the object and update both sides of the relationship*/
        this.activities.add(activity);
        activity.getIssues().add(this);

        return activity;
    }


    public Set<Activity> addActivities(Set<Activity> activities) {

        if (activities == null) /* Empty Set means nothing will execute in the forEach, so no need to check*/
            throw new RuntimeException("Null activities list passed for Project id:" + this.getId());

        activities.forEach(this::addActivity);

        return activities;
    }
}
