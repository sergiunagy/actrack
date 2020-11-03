package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sena.activitytracker.acktrack.model.ProjectUserRoles;
import sena.activitytracker.acktrack.model.UserRoleKey;
import sena.activitytracker.acktrack.repositories.ProjectUserRolesRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProjectUserRolesServiceImplTest {

    @Mock
    ProjectUserRolesRepository projectUserRolesRepository;

    @InjectMocks
    ProjectUserRolesServiceImpl projectUserRolesService;

    ProjectUserRoles roleMapping1, roleMapping2;
    Set<ProjectUserRoles> projectUserRolesSet =new HashSet<>();

    UserRoleKey key1;
    UserRoleKey key2;

    @BeforeEach
    void setUp() {

        key1 = new UserRoleKey(1L,1L);
        key2 = new UserRoleKey(1L,2L);

        /*Dummy project init*/
        roleMapping1 = ProjectUserRoles.builder()
                .userRoleKey(key1)
                .build();

        roleMapping2 = ProjectUserRoles.builder()
                .userRoleKey(key2)
                .build();


        projectUserRolesSet.add(roleMapping1);
        projectUserRolesSet.add(roleMapping2);
    }

    @Test
    void findAll() {

        when(projectUserRolesRepository.findAll()).thenReturn(projectUserRolesSet);

        Set<ProjectUserRoles> foundProjectUserRoless = projectUserRolesService.findAll();

        assertNotNull(foundProjectUserRoless);
        assertEquals(2, foundProjectUserRoless.size());
        verify(projectUserRolesRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(projectUserRolesRepository.findByUserRoleKey(any(UserRoleKey.class))).thenReturn(roleMapping1);

        ProjectUserRoles foundProjectUserRoles = projectUserRolesService.findByUserRoleKey(roleMapping1.getUserRoleKey());

        assertNotNull(foundProjectUserRoles);
        assertEquals(key1, foundProjectUserRoles.getUserRoleKey());
        verify(projectUserRolesRepository, times(1)).findByUserRoleKey(any(UserRoleKey.class));
    }

    @Test
    void save() {

        when(projectUserRolesRepository.save(any(ProjectUserRoles.class))).thenReturn(roleMapping1);

        ProjectUserRoles foundProjectUserRoles = projectUserRolesService.save(roleMapping1);

        assertNotNull(foundProjectUserRoles);
        assertEquals(key1, foundProjectUserRoles.getUserRoleKey());
        verify(projectUserRolesRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(projectUserRolesRepository.saveAll(any(Set.class))).thenReturn(projectUserRolesSet);

        Set<ProjectUserRoles> foundProjectUserRoless = projectUserRolesService.saveAll(projectUserRolesSet);

        assertNotNull(foundProjectUserRoless);
        assertEquals(2, foundProjectUserRoless.size());
        verify(projectUserRolesRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        projectUserRolesService.delete(roleMapping1);

        verify(projectUserRolesRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        projectUserRolesService.deleteById(1L);

        verify(projectUserRolesRepository, times(1)).deleteById(anyLong());
    }

}