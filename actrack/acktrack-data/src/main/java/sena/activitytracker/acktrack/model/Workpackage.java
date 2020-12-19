package sena.activitytracker.acktrack.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//import static sena.activitytracker.acktrack.model.security.BaseSecurityEntity.setNullProtection;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "workpackages")
public class Workpackage extends BaseEntity {

    @NaturalId
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "workpackages_activities",
            joinColumns = @JoinColumn(name = "workpackage_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> activities;

    @ManyToMany(mappedBy = "workpackages")
    private Set<Issue> issues = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "workpackage_user",
            joinColumns = @JoinColumn(name = "workpackage_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @Builder
    public Workpackage(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String name, String description, LocalDate startDate, LocalDate endDate, Set<Activity> activities, Set<Issue> issues, Set<User> users) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        if(activities != null) this.activities = activities;
        if(issues != null) this.issues = issues;
        if(users != null) this.users = users;
    }

    public Activity addActivity(final Activity activity) {

        /* Initialize if null set*/
        this.activities = checkedSet.apply(this.activities);
        this.activities.add(activity);

        activity.getWorkpackages().add(this); /*todo: add error management for negative branch */

        return activity;
    }

    public Set<Activity> addActivities(final Set<Activity> activities) {

        if(activities == null)
            throw new RuntimeException("Null value passed as set of activities to add to workpackage" + this.toString());

        activities.forEach(this::addActivity);

        return activities;
    }

    public User addUser(final User user) {

        /* Initialize if set*/
        this.users = checkedSet.apply(this.users);

        this.users.add(user);
        user.getWorkpackages().add(this);

        return user;
    }

    public Set<User> addUsers(final Set<User> users) {

        if(users != null)
            users.forEach(this::addUser);

        return users;
    }

}
