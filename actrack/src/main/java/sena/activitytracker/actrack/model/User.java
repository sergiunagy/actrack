package sena.activitytracker.actrack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "family_name")
    private String familyName ;

    @Column(name = "given_name")
    private String givenName ;

    @NaturalId
    @Column(name = "user_system_id")
    private String uid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Activity> activities = new HashSet<>();

    public User(Long id, String familyName, String givenName, String uid, Set<Activity> activities) {
        super(id);
        this.familyName = familyName;
        this.givenName = givenName;
        this.uid = uid;
        if(activities != null) this.activities = activities;
    }
}
