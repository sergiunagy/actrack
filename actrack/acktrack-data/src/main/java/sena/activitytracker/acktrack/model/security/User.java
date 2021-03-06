package sena.activitytracker.acktrack.model.security;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sena.activitytracker.acktrack.model.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseSecurityEntity implements UserDetails, CredentialsContainer {

    /***********************************************************************************************
     * Database behavior setup */
    /* Inherited from parent */


    /***********************************************************************************************
    * Security instance fields */

    private String username;
    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /***********************************************************************************************
     * Employee details */

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "pers_email")
    private String personalEmail;

    @Column(name = "work_email")
    private String workEmail;


    /***********************************************************************************************
     * Activity tracking properties */

    /*Todo: Lazy load for activities. Also batch the load by date*/
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user") /* remove activities if user is removed ? */
     private Set<Activity> activities = new HashSet<>();

    /* Eager fetching - JOIN fetches may return duplicates so type should be Set:
    .  Ex: https://www.solidsyntax.be/2013/10/17/fetching-collections-hibernate/*/
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Workpackage> workpackages = new HashSet<>();

    @Builder
    public User(long id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Set<Role> roles, String familyName, String givenName, Set<Activity> activities, Set<Project> projects, Set<Workpackage> workpackages) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.familyName = familyName;
        this.givenName = givenName;
        if(null!= roles) this.roles = roles;
        if(null!= activities) this.activities = activities;
        if(null!= projects) this.projects = projects;
        if(null!= workpackages) this.workpackages = workpackages;
    }

    @Transient
    public Set<GrantedAuthority> getAuthorities(){

        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
    }

    @Transient
    public Set<Issue> getIssues(){

        /*Get the issues from the activities, there may be allocated workpackages that had no real actions taken*/

        return this.getActivities().stream()
                .flatMap(activity -> activity.getWorkpackages().stream())
                .flatMap(workpackage->workpackage.getIssues().stream())
                .collect(Collectors.toSet());
    }

    /*Security accessors*/

    @Override
    public void eraseCredentials() {
        password = null;
    }

    /*******************************************************************
    * *****Adders for bidirectional relationships***********************/

    /*Roles*/
    public Set<Role> addRoles(@NonNull final Set<Role> roles){

        roles.forEach(this::addRole);
        return this.roles;
    }

    public Role addRole(@NonNull final Role role){

        this.roles = checkedSet.apply(this.roles);

        this.roles.add(role);
        role.getUsers().add(this); /* set reverse connection*/

        return role;
    };

    /*Activities*/
    public Set<Activity> addActivities(@NonNull final Set<Activity> activities) {

        activities.forEach(this::addActivity);

        return activities;
    }

    public Activity addActivity(@NonNull final Activity activity) {

        this.activities = checkedSet.apply(this.activities);

        this.activities.add(activity);
        activity.setUser(this);

        return activity;
    }

    /*Workpackages*/
    public Set<Workpackage> addWorkpackages(@NonNull final Set<Workpackage> workpackages) {

        workpackages.forEach(this::addWorkpackage);

        return workpackages;
    }


    public Workpackage addWorkpackage(@NonNull final Workpackage workpackage) {

        this.workpackages = checkedSet.apply(this.workpackages);

        this.workpackages.add(workpackage);
        workpackage.getUsers().add(this);

        return workpackage;
    }

    /*Projects*/
    public Project addProject(@NonNull final Project project){

        this.projects = checkedSet.apply(this.projects);

        this.projects.add(project);
        project.getUsers().add(this);

        return project;
    }

    public Set<Project> addProjects(@NonNull Set<Project> projects){

        projects.forEach(this::addProject);

        return this.projects;
    }
}
