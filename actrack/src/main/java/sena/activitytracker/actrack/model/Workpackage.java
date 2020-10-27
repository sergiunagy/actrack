package sena.activitytracker.actrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.atmosphere.config.service.Get;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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

    @ManyToMany(mappedBy = "workpackages")
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
}
