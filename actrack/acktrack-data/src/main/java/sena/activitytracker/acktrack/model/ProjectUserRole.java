package sena.activitytracker.acktrack.model;

import lombok.*;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProjectUserRole extends BaseEntity {
/* Each mapping is unique : one project, one role, one user*/
    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Project project;

    @Builder
    public ProjectUserRole(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp, // Base object properties
                           User user, Role role, Project project) {

        super(id, version, createdTimestamp, updatedTimestamp);
        this.user = user;
        this.role = role;
        this.project = project;
    }
}
