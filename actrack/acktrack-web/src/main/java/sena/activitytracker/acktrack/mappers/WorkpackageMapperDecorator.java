package sena.activitytracker.acktrack.mappers;

import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sena.activitytracker.acktrack.dtos.WorkpackageDTO;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.User;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class WorkpackageMapperDecorator implements WorkpackageMapper {

    private WorkpackageMapper workpackageMapper;

    @Autowired
    @Qualifier("delegate")
    public void setWorkpackageMapper(WorkpackageMapper workpackageMapper) {
        this.workpackageMapper = workpackageMapper;
    }

    @Override
    public WorkpackageDTO toWorkpackageDTO(Workpackage workpackage) {

        WorkpackageDTO workpackageDTO = workpackageMapper.toWorkpackageDTO(workpackage);

        /* Associated issues ids */
        workpackageDTO.setIssueIds(getIssueIds(workpackage));
        /* Associated activities ids */
        workpackageDTO.setActivityIds(getActivityIds(workpackage));
        /* Associated user names */
        workpackageDTO.setUserIds(getUserNames(workpackage));
        /* Hours booked on workpackage */
        workpackageDTO.setHoursBooked(getHoursBooked(workpackage));


        return workpackageDTO;
    }

    private Set<String> getActivityIds(Workpackage workpackage) {
        return workpackage.getActivities().stream()
                .map(activity -> activity.getId().toString())
                .collect(Collectors.toSet());
    }

    private int getHoursBooked(Workpackage workpackage) {

        int res = 0;
        Optional<Integer> totalOpt = workpackage.getActivities().stream()
                .map(activity -> Math.toIntExact(activity.getDuration().getSeconds()/3600))
                .reduce(Integer::sum);

        if(totalOpt.isPresent()) {
            res = totalOpt.get().intValue(); /* For issues where cast fails, return 0 . todo: include error reporting*/
        }

        return res;
    }

    private Set<String> getUserNames(Workpackage workpackage) {

        return workpackage.getActivities().stream()
                .map(Activity::getUser) /* Each Activity has one user*/
                .map(user -> user.getGivenName() + ' ' + user.getFamilyName())
                .collect(Collectors.toSet());
    }

    private Set<String> getIssueIds(Workpackage workpackage) {

        return workpackage.getIssues().stream()
                .map(Issue::getIssue_id)
                .collect(Collectors.toSet());
    }
}
