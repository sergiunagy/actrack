package sena.activitytracker.actrack.model;

import lombok.*;

import javax.persistence.*;
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
    private Set<Issue> issues;

    @ManyToMany
    @JoinTable(name = "project_roles",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "project_users",
    joinColumns = @JoinColumn(name = "project_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;


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
}
