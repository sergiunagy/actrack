package sena.activitytracker.acktrack.mappers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import sena.activitytracker.acktrack.dtos.IssueDTO;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Issue;

import java.util.Optional;

public abstract class IssueMapperDecorator implements IssueMapper {

    private IssueMapper issueMapper;

    @Autowired
    @Qualifier("delegate")
    public void setIssueMapper(IssueMapper issueMapper) {
        this.issueMapper = issueMapper;
    }

    @Override
    public IssueDTO toIssueDTO(Issue issue){

        IssueDTO issueDTO = issueMapper.toIssueDTO(issue);

        /* project name */
        issueDTO.setProjectName(issue.getProject().getName());
        /* number of Activities */
        issueDTO.setNoOfActivities(getNoOfActivities(issue));
        /* number of Workpackages */
        issueDTO.setNoOfWorkpackages(getNoOfWorkpackages(issue));
        /* hours worked*/
        issueDTO.setHoursWorked(getHoursWorked(issue));
        return issueDTO;
    }

    /**
     * Counts the hours recorded in individual Activities associated with the Issue
     * @param issue
     * @return
     */
    private int getHoursWorked(Issue issue) {

        int total = 0;
        Optional<Integer> totalOpt = issue.getActivities().stream()
                .map(activity -> Math.toIntExact(activity.getDuration().getSeconds()/3600))
                .reduce(Integer::sum);

        if(totalOpt.isPresent()) {
            total = totalOpt.get().intValue();
        }

        return total;
    }

    private int getNoOfWorkpackages(Issue issue) {

        return (int) issue.getWorkpackages().stream().count();
    }

    private int getNoOfActivities(Issue issue) {

        return (int)issue.getActivities().stream().count(); /* cast to int is OK based on use case*/
    }
}
