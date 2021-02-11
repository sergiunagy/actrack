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

import java.time.LocalDate;
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

    @Mock
    IssueMapper issueMapper;

    @InjectMocks
    IssueServiceImpl issueService;

    Issue review, quality;
    Set<Issue> issueSet =new HashSet<>();

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
        /* given*/
        IssueDTO dummyDTO = IssueDTO.builder().issue_id("dummy").createdDate(LocalDate.now()).build();
        /* when */
        when(issueRepository.findAll()).thenReturn(issueSet);
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(dummyDTO); /* findAll returns a Set.
                                                                                This will be overwritten since it is same object.
                                                                                So there is no point in counting objects, count the calls instead*/

        /* trigger */
        Set<IssueDTO> foundIssues = issueService.findAll();

        /* verify */
        assertNotNull(foundIssues);
        verify(issueRepository, times(1)).findAll();
        verify(issueMapper, times(2)).toIssueDTO(any());

    }

    @Test
    void findById() {
        /* given*/
        IssueDTO dummyDTO = IssueDTO.builder().issue_id("dummy").createdDate(LocalDate.now()).build();

        /* when */
        when(issueRepository.findById(any())).thenReturn(Optional.of(review));
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(dummyDTO);

        Optional<IssueDTO> foundIssueOptional = issueService.findById(review.getId());

        assertTrue(foundIssueOptional.isPresent());
        assertTrue(dummyDTO.getIssue_id().equals(foundIssueOptional.get().getIssue_id()));
        verify(issueRepository, times(1)).findById(any());
        verify(issueMapper, times(1)).toIssueDTO(any());

    }

    @Test
    void save() {
        /* given*/
        IssueDTO dummyDto = IssueDTO.builder().issue_id("dummy").createdDate(LocalDate.now()).build();

        /* when*/
        when(issueRepository.save(any(Issue.class))).thenReturn(review);
        when(issueMapper.toIssue(any(IssueDTO.class))).thenReturn(review);
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(dummyDto);

        /* trigger */
        IssueDTO foundIssue = issueService.save(dummyDto);

        /* verify */
        assertNotNull(foundIssue);
        verify(issueRepository, times(1)).save(any());
        verify(issueMapper, times(1)).toIssue(any());
        verify(issueMapper, times(1)).toIssueDTO(any());
    }

    @Test
    void saveAll() {

        Set<IssueDTO> issueDTOSet = new HashSet<>();
        issueDTOSet.add(IssueDTO.builder().issue_id("dummy1").createdDate(LocalDate.now()).build());
        issueDTOSet.add(IssueDTO.builder().issue_id("dummy2").createdDate(LocalDate.now()).build());

        when(issueRepository.saveAll(any(Set.class))).thenReturn(issueSet);
        when(issueMapper.toIssue(any(IssueDTO.class))).thenReturn(review);
        when(issueMapper.toIssueDTO(any(Issue.class))).thenReturn(issueDTOSet.stream().findAny().get());

        Set<IssueDTO> foundIssues = issueService.saveAll(issueDTOSet);

        assertNotNull(foundIssues);

        verify(issueRepository, times(1)).saveAll(any());
        verify(issueMapper, times(2)).toIssue(any());
        verify(issueMapper, times(2)).toIssueDTO(any());
    }

    @Test
    void delete() {

        IssueDTO dummyDto = IssueDTO.builder().issue_id("dummy").createdDate(LocalDate.now()).build();
        issueService.delete(dummyDto);

        verify(issueRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        issueService.deleteById(IDONE);

        verify(issueRepository, times(1)).deleteById(any());
    }
}