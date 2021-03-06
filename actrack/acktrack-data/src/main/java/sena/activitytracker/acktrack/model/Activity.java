package sena.activitytracker.acktrack.model;

import lombok.*;
import sena.activitytracker.acktrack.model.security.BaseSecurityEntity;
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
@Entity
@Table(name = "activities")
public class Activity extends BaseEntity implements Comparable<Activity>{

//    private static BaseCounter baseCounter;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "exported")
    private Boolean isExported;

    @ManyToMany(mappedBy = "activities")
    private Set<Workpackage> workpackages = new HashSet<>();

    @ManyToMany(mappedBy = "activities")
    private Set<Issue> issues = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Activity(Long id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, // Base object properties
                    String description, LocalDate date, Duration duration, Boolean isExported, Set<Workpackage> workpackages, Set<Issue> issues, User user) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.isExported = isExported;
        if (workpackages != null) this.workpackages = workpackages;
        if (issues != null) this.issues = issues;
        this.user = user;
    }


    public User addUser(@NonNull User user) {

        user.getActivities().add(this);
        this.user = user;

        return this.user;
    }

    @Override
    public int compareTo(Activity comparedActivity) {

        return date.compareTo(comparedActivity.date);
    }
}
