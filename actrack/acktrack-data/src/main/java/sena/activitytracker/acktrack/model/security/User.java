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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    private boolean accountNonLocked = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Builder.Default
    private boolean enabled = true;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    public Set<GrantedAuthority> getAuthorities(){

        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
    }

    /***********************************************************************************************
     * Employee details*/

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "given_name")
    private String givenName;

    /*Todo: Lazy load for activities. Also batch the load by date*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
     private Set<Activity> activities ;

    /*Eager fetching - JOIN fetches return duplicates so type should be Set:
    .  Ex: https://www.solidsyntax.be/2013/10/17/fetching-collections-hibernate/*/
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Workpackage> workpackages;

    @Override
    public void eraseCredentials() {
        password = null;
    }

    public Set<Activity> addActivities(Set<Activity> activities) {

        if (activities == null || activities.isEmpty())
            throw new RuntimeException("Null or empty activities list passed for User id:" + this.getId());

        this.activities = setNullProtection.apply(this.activities);

        for (Activity activity : activities) {
            addActivity(activity);
        }
        return activities;
    }

    public Activity addActivity(Activity activity) {

        if (activity == null)
            throw new RuntimeException("Null activity passed to addActivity for User id:" + this.getId());

        this.activities = setNullProtection.apply(this.activities);

        activity.setUser(this);
        this.activities.add(activity);

        return activity;
    }

    public Set<Workpackage> addWorkpackages(Set<Workpackage> workpackages) {

        if (workpackages == null || workpackages.isEmpty())
            throw new RuntimeException("Null workapackages list passed for Project id:" + this.getId());

        this.workpackages = setNullProtection.apply(this.workpackages);

        for (Workpackage workpackage : workpackages) {
            addWorkpackage(workpackage);
        }
        return workpackages;
    }


    public Workpackage addWorkpackage(Workpackage workpackage) {

        if (workpackage == null)
            throw new RuntimeException("Null workpackage passed to addIssue for Project id:" + this.getId());

        this.workpackages = setNullProtection.apply(this.workpackages);

        workpackage.getUsers().add(this);
        this.workpackages.add(workpackage);

        return workpackage;
    }

}
