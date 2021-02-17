package sena.activitytracker.acktrack.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO extends BaseEntityDto{

    /***********************************************************************************************
     * Security instance fields - todo - clarify DTO and Security best practices*/

    private String username;
    private String password;
    private boolean accountNonExpired ;
    private boolean accountNonLocked ;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String familyName;
    private String givenName;

    private Set<String> rolesSet;
    private Set<String> activitiyIdsBetweenDates; /* todo : Here, activities are lazy loaded on the original. Check for issues */
    private Set<String> allActivitiesIds; /* todo : this requires a service. Replace with function to simulate lazy load and avoid long start times? */
    private Set<String> allActivitiesCount; /* todo : this requires either a similar approach to  allActivitiesIds */

    private int hoursBookedBetweenDates;  /* if we ever need all hours worked we can extract the from all activities*/

    private Set<String> projectsSet = new HashSet<>(); /* todo : Project ids or names ?*/
    private Set<String> workpackagesSet = new HashSet<>(); /*  todo : Project ids or names ?*/

    /* todo:
        Ideas, maybe for reports :
    * - hours worked for each project
    * - number of associated issues currently open
    * - credentials expiration date
    * - vacation days
    * - trainings
    * */

}
