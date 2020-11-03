package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    ProjectRepository projectRepository;

    @InjectMocks
    ProjectServiceImpl projectService;

    Project alpha, beta;
    Set<Project> projectSet=new HashSet<>();

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        alpha = Project.builder()
                .id(1L)
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
                .id(2L)
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

        Set<Project> foundProjects = projectService.findAll();

        assertNotNull(foundProjects);
        assertEquals(2, foundProjects.size());
        verify(projectRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(alpha));

        Project foundProject = projectService.findById(alpha.getId());

        assertNotNull(foundProject);
        assertEquals(1, foundProject.getId());
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {

        when(projectRepository.save(any(Project.class))).thenReturn(alpha);

        Project foundProject = projectService.save(alpha);

        assertNotNull(foundProject);
        assertEquals(1, foundProject.getId());
        verify(projectRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(projectRepository.saveAll(any(Set.class))).thenReturn(projectSet);

        Set<Project> foundProjects = projectService.saveAll(projectSet);

        assertNotNull(foundProjects);
        assertEquals(2, foundProjects.size());
        verify(projectRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        projectService.delete(alpha);

        verify(projectRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        projectService.deleteById(1L);

        verify(projectRepository, times(1)).deleteById(anyLong());
    }
}