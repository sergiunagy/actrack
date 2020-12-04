package sena.activitytracker.acktrack.model.security;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import sena.activitytracker.acktrack.model.BaseEntity;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.ProjectUserRole;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",
    joinColumns = @JoinColumn(name = "ROLE_ID"),
    inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;
}
