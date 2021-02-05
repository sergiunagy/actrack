package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.mappers.ProjectMapper;
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

    @InjectMocks
    ProjectServiceImpl projectService;

    Project alpha, beta;
    Set<Project> projectSet=new HashSet<>();
    ProjectMapper projectMapper;


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

        when(projectRepository.findAll()).thenReturn(projectSet);

        Set<ProjectDTO> foundProjects = projectService.findAll();

        assertNotNull(foundProjects);
        assertEquals(2, foundProjects.size());
        verify(projectRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(projectRepository.findById(any())).thenReturn(Optional.of(alpha));

        Optional<ProjectDTO> foundProjectOptional = projectService.findById(alpha.getId());

        assertTrue(foundProjectOptional.isPresent());
        assertTrue(alpha.getId().equals(foundProjectOptional.get().getId()));
        verify(projectRepository, times(1)).findById(any());
    }

    @Test
    void save() {

        when(projectRepository.save(any(Project.class))).thenReturn(alpha);

        ProjectDTO foundProject = projectService.save(projectMapper.toProjectDTO(alpha));

        assertNotNull(foundProject);
        assertTrue(alpha.getId().equals(foundProject.getId()));
        verify(projectRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(projectRepository.saveAll(any(Set.class))).thenReturn(projectSet);

        Set<ProjectDTO> dtos = new HashSet<>();
        projectSet.forEach(project -> dtos.add(projectMapper.toProjectDTO(project)));

        Set<ProjectDTO> foundProjects = projectService.saveAll(dtos);

        assertNotNull(foundProjects);
        assertEquals(2, foundProjects.size());
        verify(projectRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        projectService.delete(projectMapper.toProjectDTO(alpha));

        verify(projectRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        projectService.deleteById(IDONE);

        verify(projectRepository, times(1)).deleteById(any());
    }
}