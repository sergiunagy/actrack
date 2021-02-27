package sena.activitytracker.acktrack.model.security;

import lombok.*;
import sena.activitytracker.acktrack.model.BaseEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
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
    private Set<User> users = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
    joinColumns = @JoinColumn(name = "ROLE_ID"),
    inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    public Role(Long id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String name, String description, Set<User> users, Set<Authority> authorities) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.name = name;
        this.description = description;
        if(users != null) this.users = users;
        if(authorities != null) this.authorities = authorities;
    }

    /* Initialize double linked relationship*/
    public Authority addAuthority(@NonNull final Authority authority){

        /*initilize the set if null*/
        this.authorities= BaseSecurityEntity.checkedSet.apply(this.authorities);

        this.authorities.add(authority);
        authority.getRoles().add(this);

        return authority;
    }

    /* Initialize double linked relationship*/
    public Set<Authority> addAllAuthorities(@NonNull final Set<Authority> authorities){

        authorities.forEach(this::addAuthority);

        return authorities;
    }
}
