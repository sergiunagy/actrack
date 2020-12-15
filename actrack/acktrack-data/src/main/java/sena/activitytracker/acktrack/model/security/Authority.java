package sena.activitytracker.acktrack.model.security;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Authority extends BaseSecurityEntity{

    private String permission;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private Set<Role> roles= new HashSet<>();
}
