package sena.activitytracker.acktrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class Activity extends BaseEntity {

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
    public Activity(Long id, String description, LocalDate date, Duration duration, Boolean isExported, Set<Workpackage> workpackages, Set<Issue> issues, User user) {
        super(id);
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.isExported = isExported;
        if (workpackages != null) this.workpackages = workpackages;
        if (issues != null) this.issues = issues;
        this.user = user;
    }


    public User addUser(User user) {

        if (user == null) throw new RuntimeException("Null User passed to activity" + this.getId());

        user.getActivities().add(this);
        this.user = user;

        return this.user;
    }
}
