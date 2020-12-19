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
    * Security instance fields*/

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
     * Employee details*/

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "given_name")
    private String givenName;

    /*Todo: Lazy load for activities. Also batch the load by date*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
     private Set<Activity> activities = new HashSet<>();

    /*Eager fetching - JOIN fetches return duplicates so type should be Set:
    .  Ex: https://www.solidsyntax.be/2013/10/17/fetching-collections-hibernate/*/
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Workpackage> workpackages = new HashSet<>();

    @Builder
    public User(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled, Set<Role> roles, String familyName, String givenName, Set<Activity> activities, Set<Project> projects, Set<Workpackage> workpackages) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        if(null!= roles) this.roles = roles;
        this.familyName = familyName;
        this.givenName = givenName;
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

    public Set<Role> addRoles(Set<Role> roles){

        roles.stream().map(this::addRole);
        return this.roles;
    }

    public Role addRole(Role role){

        this.roles.add(role);
        role.getUsers().add(this); /* set reverse connection*/

        return role;
    };

    @Override
    public void eraseCredentials() {
        password = null;
    }


    public Set<Activity> addActivities(Set<Activity> activities) {

        if (activities == null || activities.isEmpty())
            throw new RuntimeException("Null or empty activities list passed for User id:" + this.getId());

        this.activities = checkedSet.apply(this.activities);

        for (Activity activity : activities) {
            addActivity(activity);
        }
        return activities;
    }

    public Activity addActivity(Activity activity) {

        if (activity == null)
            throw new RuntimeException("Null activity passed to addActivity for User id:" + this.getId());

        this.activities = checkedSet.apply(this.activities);

        activity.setUser(this);
        this.activities.add(activity);

        return activity;
    }

    public Set<Workpackage> addWorkpackages(Set<Workpackage> workpackages) {

        if (workpackages == null)
            throw new RuntimeException("Null workpackages list passed for Project id:" + this.getId());

        this.workpackages = checkedSet.apply(this.workpackages);
        workpackages.forEach(this::addWorkpackage);

        return workpackages;
    }


    public Workpackage addWorkpackage(Workpackage workpackage) {

        if (workpackage == null)
            throw new RuntimeException("Null workpackage passed to addIssue for Project id:" + this.getId());

        this.workpackages = checkedSet.apply(this.workpackages);

        workpackage.getUsers().add(this);
        this.workpackages.add(workpackage);

        return workpackage;
    }


}
