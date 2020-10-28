package sena.activitytracker.actrack.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
public class UserRoleKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;

}
