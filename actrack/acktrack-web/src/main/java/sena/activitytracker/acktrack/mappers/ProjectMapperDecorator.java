package sena.activitytracker.acktrack.mappers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Project;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class ProjectMapperDecorator implements ProjectMapper{

    private ProjectMapper projectMapper;

    @Autowired
    @Qualifier("delegate")
    public void setProjectMapper(ProjectMapper projectMapper){
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectDTO toProjectDTO(Project project){

        /*trigger decorated class mapper*/
        ProjectDTO projectDTO = projectMapper.toProjectDTO(project);

        /* Open Issues count */
        projectDTO.setNoOpenIssues(getOpenIssuesCount(project));
        /* Allocated Team-members count */
        projectDTO.setNoTeamMembers(getTeamMembersCount(project));
        /* Consumed hours */
        projectDTO.setHoursWorked(getWorkedHoursCount(project));

        return projectDTO;
    }

    private int getWorkedHoursCount(Project project) {

        int res = 0;
        Optional<Integer> totalOpt = project.getIssues().stream()
                .flatMap(issue -> issue.getActivities().stream())
                .distinct()    /* Activities may be related to multiple Issues - take only uniques*/
                .map(activity -> Math.toIntExact(activity.getDuration().getSeconds()/3600))/* get hours*/
                .reduce(Integer::sum);

        if(totalOpt.isPresent()) {
            res = totalOpt.get().intValue(); /* For issues where cast fails, return 0 . todo: include error reporting*/
        }

        return res;
    }

    private int getTeamMembersCount(Project project) {
        return (int) project.getIssues().stream()
                .flatMap(issue -> issue.getWorkpackages().stream())
                .flatMap(workpackage -> workpackage.getActivities().stream())
                .map(activity -> activity.getUser())
                .distinct()
                .count();
    }

    private int getOpenIssuesCount(Project project) {

        /*Assume issues with no closed date are still open*/
        return (int) project.getIssues().stream()
                .filter(issue -> issue.getClosedDate()==null)
                .distinct()
                .count();
    }

}
