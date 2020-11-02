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
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "given_name")
    private String givenName;

    @NaturalId
    @Column(name = "user_system_id")
    private String uid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Workpackage> workpackages = new HashSet<>();

    @Builder
    public User(Long id, String familyName, String givenName, String uid, Set<Activity> activities, Set<Project> projects, Set<Workpackage> workpackages) {
        super(id);
        this.familyName = familyName;
        this.givenName = givenName;
        this.uid = uid;
        if (activities != null) this.activities = activities;
        if (projects != null) this.projects = projects;
        if (workpackages != null) this.workpackages = workpackages;
    }

    public Set<Activity> addActivities(Set<Activity> activities) {

        if (activities == null || activities.isEmpty())
            throw new RuntimeException("Null or empty activities list passed for User id:" + this.getId());

        for (Activity activity : activities) {
            addActivity(activity);
        }
        return activities;
    }

    public Activity addActivity(Activity activity) {

        if (activity == null)
            throw new RuntimeException("Null activity passed to addActivity for User id:" + this.getId());

        activity.setUser(this);
        this.activities.add(activity);

        return activity;
    }


    public Set<Workpackage> addWorkpackages(Set<Workpackage> workpackages) {

        if (workpackages == null || workpackages.isEmpty())
            throw new RuntimeException("Null workapackages list passed for Project id:" + this.getId());

        for (Workpackage workpackage : workpackages) {
            addWorkpackage(workpackage);
        }
        return workpackages;
    }

    public Workpackage addWorkpackage(Workpackage workpackage) {

        if (workpackage == null)
            throw new RuntimeException("Null workpackage passed to addIssue for Project id:" + this.getId());

        workpackage.getUsers().add(this);
        this.workpackages.add(workpackage);

        return workpackage;
    }
}
