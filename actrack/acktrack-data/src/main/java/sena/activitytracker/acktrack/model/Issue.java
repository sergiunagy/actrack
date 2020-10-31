package sena.activitytracker.acktrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="issues")
public class Issue extends BaseEntity {

    @Column(name = "short_description")
    private String shortName;

    @NaturalId
    @Column(name = "issue_tracking_id")
    private String issue_id;

    @Column(name = "description")
    private String description;

    @Column(name = "link_to_issue")
    private String link;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "issues_workpackages",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "workpackage_id"))
    private Set<Workpackage> workpackages = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "issues_activities",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Issue(Long id, String shortName, String issue_id, String description, String link, Set<Workpackage> workpackages, Set<Activity> activities) {
        super(id);
        this.shortName = shortName;
        this.issue_id = issue_id;
        this.description = description;
        this.link = link;
        if( activities != null) this.activities = activities;
        if( workpackages != null) this.workpackages = workpackages;
    }

    /*Helper methods*/
    public Set<Workpackage> addWorkpackages(Set<Workpackage> workpackages) {

        if (workpackages == null || workpackages.isEmpty())
            throw new RuntimeException("Null workapackages list passed for Project id:" + this.getId());

        for (Workpackage issue : workpackages) {
            addWorkpackage(issue);
        }
        return workpackages;
    }

    public Workpackage addWorkpackage(Workpackage workpackage) {

        if (workpackage == null)
            throw new RuntimeException("Null workpackage passed to addIssue for Project id:" + this.getId());

        workpackage.getIssues().add(this);
        this.workpackages.add(workpackage);

        return workpackage;
    }

    public Set<Activity> addActivities(Set<Activity> activities) {

        if (activities == null || activities.isEmpty())
            throw new RuntimeException("Null activities list passed for Project id:" + this.getId());

        for (Activity activity : activities) {
            addActivity(activity);
        }
        return activities;
    }

    public Activity addActivity(Activity activity) {

        if (activity == null)
            throw new RuntimeException("Null activity passed to addIssue for Project id:" + this.getId());

        activity.getIssues().add(this);
        this.activities.add(activity);

        return activity;
    }
}
