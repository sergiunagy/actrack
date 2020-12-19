package sena.activitytracker.acktrack.model.security;

import lombok.*;
import sena.activitytracker.acktrack.model.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseSecurityEntity {

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

    @Builder
    public Role(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String name, String description, Set<User> users, Set<Authority> authorities) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.name = name;
        this.description = description;
        this.users = users;
        this.authorities = authorities;
    }

    /* Initialize double linked relationship*/
    public Authority addAuthority(final Authority authority){

        /*initilize the set if null*/
        this.authorities= BaseSecurityEntity.checkedSet.apply(this.authorities);
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
