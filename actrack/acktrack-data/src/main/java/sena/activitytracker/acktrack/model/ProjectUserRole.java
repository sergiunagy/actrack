package sena.activitytracker.acktrack.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    public ProjectUserRole(Long id, User user, Role role, Project project) {
        super(id);
        this.user = user;
        this.role = role;
        this.project = project;
    }
}
