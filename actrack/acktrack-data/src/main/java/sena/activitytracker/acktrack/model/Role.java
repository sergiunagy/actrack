package sena.activitytracker.acktrack.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<Project> projects = new HashSet<>();

    @Builder
    public Role(Long id, String name, String description, Set<Project> projects) {
        super(id);
        this.name = name;
        this.description = description;
        if(projects !=null) this.projects = projects;
    }

    public Set<Project> addProjects(Set<Project> projects) {

        if (projects == null || projects.isEmpty())
            throw new RuntimeException("Null issue passed for Project id:" + this.getId());

        for (Project project : projects) {
            addProject(project);
        }
        return projects;
    }

    public Project addProject(Project project) {

        if (project == null)
            throw new RuntimeException("Null issue passed to addIssue for Project id:" + this.getId());

        if(!project.getRoles().stream().anyMatch(role -> role.getId()==this.getId())){
            project.getRoles().add(this);
        }
        this.getProjects().add(project);

        return project;
    }
}
