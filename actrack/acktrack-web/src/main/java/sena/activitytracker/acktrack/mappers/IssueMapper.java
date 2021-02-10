package sena.activitytracker.acktrack.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import sena.activitytracker.acktrack.dtos.IssueDTO;
import sena.activitytracker.acktrack.model.Issue;

@Mapper
@DecoratedWith(IssueMapperDecorator.class)
public interface IssueMapper {

    IssueDTO toIssueDTO(Issue issue);
    Issue toIssue(IssueDTO issue);
}
