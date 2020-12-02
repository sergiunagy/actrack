package sena.activitytracker.acktrack.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "description")
    private String description;
    @Lob
    @Column(name = "notes")
    private String notes;
    @Column(name = "main_location")
    private String mainLocation;
    @Column(name = "planned_start_date")
    private LocalDate plannedStartDate;
    @Column(name = "actual_start_date")
    private LocalDate actualStartDate;
    @Column(name = "planned_end_date")
    private LocalDate plannedEndDate;
    @Column(name = "actual_end_date")
    private LocalDate actualEndDate;
    @Column(name = "planned_start_of_production_date")
    private LocalDate plannedSopDate;
    @Column(name = "actual_start_of_production_date")
    private LocalDate actualSopDate;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "product_line")
    private String productLine;
    @Column(name = "active")
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "project")
    private Set<Issue> issues = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Fetch(FetchMode.JOIN) /*No of users expected to be small*/
    private Set<User> users = new HashSet<>();


    @Builder
    public Project(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, // Base object properties
                   String name, String description, String notes, String mainLocation, LocalDate plannedStartDate, LocalDate actualStartDate, LocalDate plannedEndDate, LocalDate actualEndDate, LocalDate plannedSopDate, LocalDate actualSopDate, String customerName, String customerId, String productLine, Boolean active, Set<Issue> issues, Set<Role> roles, Set<User> users, Set<ProjectUserRole> projectUserRoles) {

        super(id, version, createdTimestamp, updatedTimestamp);
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
        if (issues != null) this.issues = issues;
        if (users != null) this.users = users;
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

    public Set<User> addUsers(Set<User> users) {

        if (users == null || users.isEmpty())
            throw new RuntimeException("Null issue passed for Project id:" + this.getId());

        for (User user : users) {
            addUser(user);
        }
        return users;
    }

    /*todo: check if user exists and only add if not already existing*/
    @Transactional
    public User addUser(User user) {

        if (user == null)
            throw new RuntimeException("Null issue passed to addIssue for Project id:" + this.getId());
        /*if the user already exists,*/
//        if(!user.isNew())
//            return user;

        if (!user.getProjects().stream().anyMatch(project -> project.getId() == this.getId())) {
            user.getProjects().add(this);
        }
        this.users.add(user);

        return user;
    }


    private boolean userExistsForProject(User user){

        return users.stream().anyMatch(usr -> usr.getId()==user.getId());
    }

    @Override
    public String toString(){

        return ("Project name: " + this.name + " with id: " + this.getId());
    }
}
