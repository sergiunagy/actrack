package sena.activitytracker.acktrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "workpackages")
public class Workpackage extends BaseEntity{

    @NaturalId
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "workpackages_activities",
            joinColumns = @JoinColumn(name = "workpackage_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "workpackages")
    private Set<Issue> issues= new HashSet<>();


    @Builder
    public Workpackage(Long id, String name, String description, Set<Activity> activities, Set<Issue> issues) {
        super(id);
        this.name = name;
        this.description = description;
        if( activities != null) this.activities = activities;
        if( issues != null) this.issues = issues;
    }

    public Set<Activity> addActivities(Set<Activity> activities) {

        if (activities == null || activities.isEmpty())
            throw new RuntimeException("Null or empty activities list passed for workpackage id:" + this.getId());

        for (Activity activity : activities) {
            addActivity(activity);
        }
        return activities;
    }

    public Activity addActivity(Activity activity) {

        if (activity == null)
            throw new RuntimeException("Null activity passed to addActivity for workpackage id:" + this.getId());

        activity.getWorkpackages().add(this);
        this.activities.add(activity);

        return activity;
    }
}
