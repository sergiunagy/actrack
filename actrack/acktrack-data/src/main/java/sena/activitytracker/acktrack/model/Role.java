package sena.activitytracker.acktrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<Project> project;

    @Builder
    public Role(Long id, String name, String description, Set<Project> project) {
        super(id);
        this.name = name;
        this.description = description;
        if(project!=null) this.project = project;
    }
}
