package sena.activitytracker.acktrack.model;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project extends BaseEntity {

    private String name;
    private String description;
    private String notes;
    private String mainLocation;
    private String plannedStartDate;
    private String actualStartDate;
    private String plannedEndDate;
    private String actualEndDate;
    private String plannedSopDate;
    private String actualSopDate;
    private String customerName;
    private String customerId;
    private String productLine;
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "project")
    private Set<Issue> issues = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "project_roles",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    /* Connecting the embedded user to role mapping */
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "project")
    private Set<ProjectUserRoles> projectUserRoles = new HashSet<>();

    @Builder
    public Project(Long id, String name, String description, String notes, String mainLocation, String plannedStartDate, String actualStartDate, String plannedEndDate, String actualEndDate, String plannedSopDate, String actualSopDate, String customerName, String customerId, String productLine, Boolean active) {
        super(id);
        this.name = name;
        this.description = description;
        this.notes = notes;
        this.mainLocation = mainLocation;
        this.plannedStartDate = plannedStartDate;
        this.actualStartDate = actualStartDate;
        this.plannedEndDate = plannedEndDate;
        this.actualEndDate = actualEndDate;
        this.plannedSopDate = plannedSopDate;
        this.actualSopDate = actualSopDate;
        this.customerName = customerName;
        this.customerId = customerId;
        this.productLine = productLine;
        this.active = active;
    }

    /*Helper methods*/
    public Set<Issue> addIssues(Set<Issue> issues) {

        if (issues == null || issues.isEmpty())
            throw new RuntimeException("Null issue passed for Project id:" + this.getId());

        for (Issue issue : issues) {
            addIssue(issue);
        }
        return issues;
    }

    public Issue addIssue(Issue issue) {

        if (issue == null)
            throw new RuntimeException("Null issue passed to addIssue for Project id:" + this.getId());

        issue.setProject(this);
        this.issues.add(issue);

        return issue;
    }
    @Transactional
    public Set<Role> addRoles(Set<Role> roles) {

        if (roles == null || roles.isEmpty())
            throw new RuntimeException("Null issue passed for Project id:" + this.getId());

        for (Role role : roles) {
            addRole(role);
        }
        return roles;
    }

    @Transactional
    public Role addRole(Role role) {

        if (role == null)
            throw new RuntimeException("Null issue passed to addIssue for Project id:" + this.getId());

        if(!role.getProjects().stream().anyMatch(project -> project.getId()==this.getId())){
            role.getProjects().add(this);
        }
        this.roles.add(role);

        return role;
    }

    public Set<User> addUsers(Set<User> users) {

        if (users == null || users.isEmpty())
            throw new RuntimeException("Null issue passed for Project id:" + this.getId());

        for (User user : users) {
            addUser(user);
        }
        return users;
    }

    @Transactional
    public User addUser(User user) {

        if (user == null)
            throw new RuntimeException("Null issue passed to addIssue for Project id:" + this.getId());

        if(!user.getProject().stream().anyMatch(project -> project.getId()==this.getId())){
            user.getProject().add(this);
        }
        this.users.add(user);

        return user;
    }

    public Set<ProjectUserRoles> addProjectUserRolesSet(Set<ProjectUserRoles> projectUserRolesSet) {

        if (projectUserRolesSet == null || projectUserRolesSet.isEmpty())
            throw new RuntimeException("Null issue passed for Project id:" + this.getId());

        for (ProjectUserRoles projectUserRoles : projectUserRolesSet) {
            addProjectUserRoles(projectUserRoles);
        }
        return projectUserRolesSet;
    }

    public ProjectUserRoles addProjectUserRoles(ProjectUserRoles projectUserRoles) {

        if (projectUserRoles == null)
            throw new RuntimeException("Null issue passed to addIssue for Project id:" + this.getId());

        projectUserRoles.setProject(this);
        this.projectUserRoles.add(projectUserRoles);

        return projectUserRoles;
    }
}
