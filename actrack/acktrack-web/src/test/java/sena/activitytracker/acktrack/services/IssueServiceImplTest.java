package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.dtos.IssueDTO;
import sena.activitytracker.acktrack.mappers.IssueMapper;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.repositories.IssueRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class IssueServiceImplTest extends BaseServiceTest{

    @Mock
    IssueRepository issueRepository;

    @InjectMocks
    IssueServiceImpl issueService;

    Issue review, quality;
    Set<Issue> issueSet =new HashSet<>();
    IssueMapper issueMapper;

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        quality = Issue.builder()
                .id(IDONE)
                .issue_id("14a8")
                .description("quality issue alpha")
                .link("url quality")
                .build();

        review = Issue.builder()
                .id(IDTWO)
                .issue_id("aaaa")
                .description("review issue beta")
                .link("url review")
                .build();


        issueSet.add(review);
        issueSet.add(quality);
    }

    @Test
    void findAll() {

        when(issueRepository.findAll()).thenReturn(issueSet);

        Set<IssueDTO> foundIssues = issueService.findAll();

        assertNotNull(foundIssues);
        assertEquals(2, foundIssues.size());
        verify(issueRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(issueRepository.findById(any())).thenReturn(Optional.of(review));

        Optional<IssueDTO> foundIssueOptional = issueService.findById(review.getId());

        assertTrue(foundIssueOptional.isPresent());
        assertTrue(review.getId().equals(foundIssueOptional.get().getId()));
        verify(issueRepository, times(1)).findById(any());
    }

    @Test
    void save() {

        when(issueRepository.save(any(Issue.class))).thenReturn(review);

        IssueDTO foundIssue = issueService.save(issueMapper.toIssueDTO(review));

        assertNotNull(foundIssue);
        assertTrue(review.getId().equals(foundIssue.getId()));
        verify(issueRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(issueRepository.saveAll(any(Set.class))).thenReturn(issueSet);
        Set<IssueDTO> issueDTOSet = new HashSet<>();
        issueSet.forEach(issue -> issueDTOSet.add(issueMapper.toIssueDTO(issue)));
        Set<IssueDTO> foundIssues = issueService.saveAll(issueDTOSet);

        assertNotNull(foundIssues);
        assertEquals(2, foundIssues.size());
        verify(issueRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        issueService.delete(issueMapper.toIssueDTO(review));

        verify(issueRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        issueService.deleteById(IDONE);

        verify(issueRepository, times(1)).deleteById(any());
    }
}