package sena.activitytracker.acktrack.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.security.BaseSecurityEntity;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Project(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String name, String description, String notes, String mainLocation, LocalDate plannedStartDate, LocalDate actualStartDate, LocalDate plannedEndDate, LocalDate actualEndDate, LocalDate plannedSopDate, LocalDate actualSopDate, String customerName, String customerId, String productLine, Boolean active, Set<Issue> issues, Set<User> users) {
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


    @Transient
    public Set<Workpackage> getWorkpackages(){

        /*Get workpackages from associated issues*/
        return this.issues.stream()
                .flatMap(issue -> issue.getWorkpackages().stream())
                .collect(Collectors.toSet());
    }

    @Transient
    public Set<Activity> getActivities(){

        return this.issues.stream()
                .flatMap(issue -> issue.getWorkpackages().stream())
                .flatMap(workpackage -> workpackage.getActivities().stream())
                .collect(Collectors.toSet());
    }

    /*Helper methods*/
    public Set<Issue> addIssues(@NonNull Set<Issue> issues) {

        issues.forEach(this::addIssue);

        return issues;
    }

    public Issue addIssue(@NonNull Issue issue) {

        this.issues = BaseEntity.checkedSet.apply(this.issues);

        this.issues.add(issue);
        issue.setProject(this);

        return issue;
    }

    public Set<User> addUsers(@NonNull Set<User> users) {

        users.forEach(this::addUser);

        return users;
    }

    @Transactional
    public User addUser(@NonNull User user) {


        /*if the user already exists don't add - a new user may register update timestamps otherwise*/
        if(this.users.stream().noneMatch(user1 -> user1.getUsername()==user.getUsername())){
            user.getProjects().add(this);
            this.users.add(user);
        }

        return user;
    }


    private boolean userExistsForProject(@NonNull User user){

        return users.stream().anyMatch(usr -> usr.getId()==user.getId());
    }

    @Override
    public String toString(){

        return ("Project name: " + this.name + " with id: " + this.getId());
    }
}
