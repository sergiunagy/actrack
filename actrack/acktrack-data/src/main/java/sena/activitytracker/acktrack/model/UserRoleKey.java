package sena.activitytracker.acktrack.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.IdClass;
import java.io.Serializable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}
