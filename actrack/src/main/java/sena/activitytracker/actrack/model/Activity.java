package sena.activitytracker.actrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="activities")
public class Activity extends BaseEntity{

    @Column(name = "description")
    private String description;

    @Column(name = "start_date_time")
    private String startDateTime;

    @Column(name = "end_date_time")
    private String endDateTime;

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
    public Activity(Long id, String description, String startDateTime, String endDateTime, Boolean isExported, Set<Workpackage> workpackages, User user) {
        super(id);
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isExported = isExported;
        if(workpackages != null) this.workpackages = workpackages;
        this.user = user;
    }
}
