package sena.activitytracker.acktrack.model.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import sena.activitytracker.acktrack.model.BaseEntity;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.ProjectUserRole;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    @Fetch(FetchMode.JOIN) /* Load all projects for roles in one go - small number*/
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<ProjectUserRole> projectUserRoles = new HashSet<>();

    @Builder
    public Role(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, // Base object properties
                String name, String description, Set<Project> projects, Set<ProjectUserRole> projectUserRoles) {

        super(id, version, createdTimestamp, updatedTimestamp);
        this.name = name;
        this.description = description;
        if(projects !=null) this.projects = projects;
        if(projectUserRoles!= null) this.projectUserRoles = projectUserRoles;
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
