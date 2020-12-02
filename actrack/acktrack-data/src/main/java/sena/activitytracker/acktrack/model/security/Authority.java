package sena.activitytracker.acktrack.model.security;

import lombok.*;

import javax.persistence.Entity;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Authority extends BaseSecurityEntity{

    private String permission;

//    private Set<Role> roles;
}
