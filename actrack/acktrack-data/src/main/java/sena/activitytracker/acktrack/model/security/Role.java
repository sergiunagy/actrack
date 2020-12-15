package sena.activitytracker.acktrack.model.security;

import lombok.*;
import lombok.experimental.Delegate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import sena.activitytracker.acktrack.model.BaseEntity;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.ProjectUserRole;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static sena.activitytracker.acktrack.model.security.BaseSecurityEntity.setNullProtection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
    joinColumns = @JoinColumn(name = "ROLE_ID"),
    inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    /* Initialize double linked relationship*/
    public Authority addAuthority(final Authority authority){

        /*initilize the set if null*/
        this.authorities= setNullProtection.apply(this.authorities);
        this.authorities.add(authority);
        if(authority.getRoles() != null)  authority.getRoles().add(this); /* todo: error management*/

        return authority;
    }

    /* Initialize double linked relationship*/
    public Set<Authority> addAllAuthorities(final Set<Authority> authorities){

        if(null != authorities)
            authorities.forEach(this::addAuthority);

        return authorities;
    }
}
