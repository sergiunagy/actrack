package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.repositories.IssueRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    @Override
    public Set<Issue> findAll() {
        Set<Issue> issues = new HashSet<>();
        issueRepository.findAll().forEach(issues::add);

        return issues;
    }

    @Override
    public Issue findById(UUID id) {

        return issueRepository.findById(id).orElse(null);
    }

    @Override
    public Issue save(Issue issue) {

        return issueRepository.save(issue);
    }

    @Override
    public Set<Issue> saveAll(Set<Issue> issues) {
        Set<Issue> retIssues = new HashSet<>();
        issueRepository.saveAll(issues).forEach(retIssues::add);

        return retIssues;
    }

    @Override
    public void delete(Issue issue) {

        issueRepository.delete(issue);
    }

    @Override
    public void deleteById(UUID id) {
        issueRepository.deleteById(id);
    }
}

