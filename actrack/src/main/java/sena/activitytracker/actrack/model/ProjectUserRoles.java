package sena.activitytracker.actrack.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class ProjectUserRoles extends BaseEntity{

    @EmbeddedId
    private UserRoleKey userRoleKey;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    private Role role;

    @Builder
    public ProjectUserRoles(Long id, UserRoleKey userRoleKey, User user, Role role) {
        super(id);
        this.userRoleKey = userRoleKey;
        this.user = user;
        this.role = role;
    }

    @ManyToOne
    private Project project;

}
