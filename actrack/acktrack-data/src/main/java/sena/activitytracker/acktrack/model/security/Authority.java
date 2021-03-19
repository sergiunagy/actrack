package sena.activitytracker.acktrack.model.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Authority extends BaseSecurityEntity implements GrantedAuthority {

    private String permission;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private Set<Role> roles= new HashSet<>();

    @Builder
    public Authority(Long id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, String permission, Set<Role> roles) {
        super(id, version, createdTimestamp, updatedTimestamp);
        this.permission = permission;
        if(roles != null) this.roles = roles;
    }

    @Override
    public String getAuthority() {

        /*TODO */
        return null;
    }
}
