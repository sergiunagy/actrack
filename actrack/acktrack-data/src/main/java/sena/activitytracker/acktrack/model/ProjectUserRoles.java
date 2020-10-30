package sena.activitytracker.acktrack.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProjectUserRoles implements Serializable {

    @EmbeddedId
    private UserRoleKey userRoleKey;

    @ManyToOne
    @MapsId("userId")
    private User userId;

    @ManyToOne
    @MapsId("roleId")
    private Role roleId;

    @ManyToOne
    private Project project;

    @Builder
    public ProjectUserRoles(UserRoleKey userRoleKey, User user, Role role, Project project) {
        this.userRoleKey = userRoleKey;
        this.userId = user;
        this.roleId = role;
        this.project = project;
    }
}
