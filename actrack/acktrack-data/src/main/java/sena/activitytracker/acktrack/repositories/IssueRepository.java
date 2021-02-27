package sena.activitytracker.acktrack.repositories;

import org.springframework.data.repository.CrudRepository;
import sena.activitytracker.acktrack.model.Issue;

import java.util.UUID;

public interface IssueRepository extends CrudRepository<Issue, Long> {
}
