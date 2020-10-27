package sena.activitytracker.actrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProjectRoles extends BaseEntity{

    private Set<String> roles = new HashSet<>();
}
