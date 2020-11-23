package sena.activitytracker.acktrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

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
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(mappedBy = "workpackages")
    private Set<Issue> issues = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "workpackage_user",
            joinColumns = @JoinColumn(name = "workpackage_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @Builder
    public Workpackage(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, // Base object properties
                       String name, String description, LocalDate startDate, LocalDate endDate, Set<Activity> activities, Set<Issue> issues, Set<User> users) {

        super(id, version, createdTimestamp, updatedTimestamp);
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        if (activities != null) this.activities = activities;
        if (issues != null) this.issues = issues;
        if(users!=null) this.users = users;
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

    public Set<User> addUsers(Set<User> users) {

        if (users == null || users.isEmpty())
            throw new RuntimeException("Null or empty activities list passed for workpackage id:" + this.getId());

        for (User user : users) {
            addUser(user);
        }
        return users;
    }

    public User addUser(User user) {

        if (user == null)
            throw new RuntimeException("Null activity passed to addActivity for workpackage id:" + this.getId());

        user.getWorkpackages().add(this);
        this.users.add(user);

        return user;
    }

}
