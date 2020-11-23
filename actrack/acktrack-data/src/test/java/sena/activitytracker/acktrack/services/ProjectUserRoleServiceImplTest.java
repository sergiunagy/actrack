package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.ProjectUserRole;
import sena.activitytracker.acktrack.model.Role;
import sena.activitytracker.acktrack.model.User;
import sena.activitytracker.acktrack.repositories.ProjectUserRolesRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
class ProjectUserRoleServiceImplTest extends BaseServiceTest{

    @Mock
    ProjectUserRolesRepository projectUserRolesRepository;

    @InjectMocks
    ProjectUserRolesServiceImpl projectUserRolesService;

    Project project;
    User sergiu, mihai;
    Role developer;
    Set<User> users;
    Set<Role> roles;
    ProjectUserRole map_role_dev, map_role_dev2;
    Set<ProjectUserRole> projectUserRoleSet = new HashSet<>();


    @BeforeEach
    void setUp() {

        sergiu= User.builder().id(IDONE).givenName("Sergiu").build();
        mihai = User.builder().id(IDTWO).givenName("Mihai").build();
        users = new HashSet<>();
        users.add(sergiu);
        users.add(mihai);

        developer = Role.builder().id(IDONE).name("developer").build();
        roles = new HashSet<>();
        roles.add(developer);

        project = Project.builder()
                .id(IDONE)
                .name("alpha")
                .description("dummy alpha")
                .mainLocation("Alpha location")
                .plannedEndDate(LocalDate.of(2018,10,20))
                .actualEndDate(LocalDate.of(2020,10,20))
                .plannedSopDate(LocalDate.of(2019,6,20))
                .plannedEndDate(LocalDate.of(2020,10,1))
                .customerName("Alpha Daimler")
                .customerId("12s42")
                .productLine("alpha moto")
                .active(true)
                .build();

        project.addUsers(users);
        project.addRoles(roles);

        map_role_dev = project.addUserToRole(sergiu, developer);
        map_role_dev2 = project.addUserToRole(mihai, developer);
        /*Ids are normally generated but for this test we do not have the database to do it*/
        map_role_dev.setId(IDONE);
        map_role_dev2.setId(IDTWO);

        projectUserRoleSet.add(map_role_dev);
        projectUserRoleSet.add(map_role_dev2);
    }

    @Test
    void findAll() {

        when(projectUserRolesRepository.findAll()).thenReturn(projectUserRoleSet);

        Set<ProjectUserRole> foundProjectUserRolesses = projectUserRolesService.findAll();

        assertNotNull(foundProjectUserRolesses);
        assertEquals(2, foundProjectUserRolesses.size());
        verify(projectUserRolesRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(projectUserRolesRepository.findById(any())).thenReturn(Optional.of(map_role_dev));

        ProjectUserRole foundProjectUserRole = projectUserRolesService.findById(map_role_dev.getId());

        assertNotNull(foundProjectUserRole);
        verify(projectUserRolesRepository, times(1)).findById(any());
    }

    @Test
    void save() {

        when(projectUserRolesRepository.save(any(ProjectUserRole.class))).thenReturn(map_role_dev);

        ProjectUserRole foundProjectUserRole = projectUserRolesService.save(map_role_dev);

        assertNotNull(foundProjectUserRole);
        verify(projectUserRolesRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(projectUserRolesRepository.saveAll(any(Set.class))).thenReturn(projectUserRoleSet);

        Set<ProjectUserRole> foundProjectUserRolesses = projectUserRolesService.saveAll(projectUserRoleSet);

        assertNotNull(foundProjectUserRolesses);
        assertEquals(2, foundProjectUserRolesses.size());
        verify(projectUserRolesRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        projectUserRolesService.delete(map_role_dev);

        verify(projectUserRolesRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        projectUserRolesService.deleteById(IDONE);

        verify(projectUserRolesRepository, times(1)).deleteById(any());
    }

}