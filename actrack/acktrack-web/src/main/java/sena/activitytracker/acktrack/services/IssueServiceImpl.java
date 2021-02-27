package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.dtos.IssueDTO;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.mappers.IssueMapper;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.repositories.IssueRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    @Override
    public Set<IssueDTO> findAll() {
        Set<IssueDTO> issueDTOSet = new HashSet<>();

        issueRepository.findAll().forEach(
                issue -> issueDTOSet.add(
                        issueMapper.toIssueDTO(issue)));

        return issueDTOSet;
    }

    @Override
    public Optional<IssueDTO> findById(@NonNull Long id) {

        Optional<Issue> issueOptional = issueRepository.findById(id);

        return Optional.of(issueMapper.toIssueDTO(issueOptional.get())); /* If we get a null behind the optional return that*/
    }

    @Override
    public IssueDTO save(@NonNull IssueDTO issueDTO) {

        Issue savedIssue = issueRepository.save(issueMapper.toIssue(issueDTO));

        return issueMapper.toIssueDTO(savedIssue);
    }

    @Override
    public Set<IssueDTO> saveAll(Set<IssueDTO> issueDTOs) {

        Set<Issue> issues = new HashSet<>();
        Set<IssueDTO> savedIssueDTOs = new HashSet<>();

        issueDTOs.forEach(issueDTO -> issues.add(issueMapper.toIssue(issueDTO)));

        issueRepository.saveAll(issues).forEach(issue -> savedIssueDTOs.add(issueMapper.toIssueDTO(issue)));

        return savedIssueDTOs;
    }

    @Override
    public void delete(IssueDTO issueDTO) {

        issueRepository.delete(issueMapper.toIssue(issueDTO));
    }

    @Override
    public void deleteById(Long id) {
        issueRepository.deleteById(id);
    }
}

