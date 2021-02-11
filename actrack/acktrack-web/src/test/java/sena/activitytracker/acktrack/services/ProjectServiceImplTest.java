package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.dtos.IssueDTO;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.mappers.ProjectMapper;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest extends BaseServiceTest{

    @Mock
    ProjectRepository projectRepository;
    @Mock
    ProjectMapper projectMapper;

    @InjectMocks
    ProjectServiceImpl projectService;

    Project alpha, beta;
    Set<Project> projectSet=new HashSet<>();


    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        alpha = Project.builder()
                .id(IDONE)
                .name("alpha")
                .description("dummy alpha")
                .mainLocation("Alpha location")
                .plannedEndDate(LocalDate.of(2018, 10, 20))
                .actualEndDate(LocalDate.of(2020, 10, 20))
                .plannedSopDate(LocalDate.of(2019, 6, 20))
                .plannedEndDate(LocalDate.of(2020, 10, 1))
                .customerName("Alpha Daimler")
                .customerId("12s42")
                .productLine("alpha moto")
                .active(true)
                .build();

        beta = Project.builder()
                .id(IDTWO)
                .name("beta")
                .description("dummy beta")
                .mainLocation("Beta location")
                .plannedEndDate(LocalDate.of(2018, 10, 20))
                .actualEndDate(LocalDate.of(2020, 10, 20))
                .plannedSopDate(LocalDate.of(2019, 6, 20))
                .plannedEndDate(LocalDate.of(2020, 10, 1))
                .customerName("Alpha Daimler")
                .customerId("13s42")
                .productLine("Beta moto")
                .active(true)
                .build();

        projectSet.add(alpha);
        projectSet.add(beta);
    }

    @Test
    void findAll() {

        /* given*/
        ProjectDTO dummyDTO = ProjectDTO.builder().build();
        /* when */
        when(projectRepository.findAll()).thenReturn(projectSet);
        when(projectMapper.toProjectDTO(any(Project.class))).thenReturn(dummyDTO); /* findAll returns a Set.
                                                                                This will be overwritten since it is same object.
                                                                                So there is no point in counting objects, count the calls instead*/
        /* trigger */
        Set<ProjectDTO> foundProjects = projectService.findAll();

        assertNotNull(foundProjects);
        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, times(2)).toProjectDTO(any());
    }

    @Test
    void findById() {

        /* given*/
        ProjectDTO dummyDTO = ProjectDTO.builder().build();

        /* when */
        when(projectRepository.findById(any())).thenReturn(Optional.of(alpha));
        when(projectMapper.toProjectDTO(any(Project.class))).thenReturn(dummyDTO);

        Optional<ProjectDTO> foundProjectOptional = projectService.findById(alpha.getId());

        assertTrue(foundProjectOptional.isPresent());
        verify(projectRepository, times(1)).findById(any());
        verify(projectMapper, times(1)).toProjectDTO(any());

    }

    @Test
    void save() {
        ProjectDTO dummyDto = ProjectDTO.builder().build();

        when(projectRepository.save(any(Project.class))).thenReturn(alpha);
        when(projectMapper.toProject(any(ProjectDTO.class))).thenReturn(alpha);
        when(projectMapper.toProjectDTO(any(Project.class))).thenReturn(dummyDto);

        ProjectDTO foundProject = projectService.save(dummyDto);

        assertNotNull(foundProject);
        verify(projectRepository, times(1)).save(any());
        verify(projectMapper, times(1)).toProject(any());
        verify(projectMapper, times(1)).toProjectDTO(any());
    }

    @Test
    void saveAll() {

        Set<ProjectDTO> dtoSet = new HashSet<>();
        dtoSet.add(ProjectDTO.builder().build());
        dtoSet.add(ProjectDTO.builder().build());

        when(projectRepository.saveAll(any(Set.class))).thenReturn(projectSet);
        when(projectMapper.toProject(any(ProjectDTO.class))).thenReturn(alpha);
        when(projectMapper.toProjectDTO(any(Project.class))).thenReturn(dtoSet.stream().findAny().get());

        Set<ProjectDTO> foundProjects = projectService.saveAll(dtoSet);

        assertNotNull(foundProjects);
        verify(projectRepository, times(1)).saveAll(any());
        verify(projectMapper, times(2)).toProject(any());
        verify(projectMapper, times(2)).toProjectDTO(any());

    }

    @Test
    void delete() {
        ProjectDTO dummyDto = ProjectDTO.builder().build();

        projectService.delete(dummyDto);

        verify(projectRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        projectService.deleteById(IDONE);

        verify(projectRepository, times(1)).deleteById(any());
    }
}