package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.repositories.security.RoleRepository;
import sena.activitytracker.acktrack.services.security.RoleServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest extends BaseServiceTest{

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleService;

    Role developer, lead;
    Set<Role> rolesSet =new HashSet<>();

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        developer = Role.builder()
                .id(IDONE)
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();

        lead = Role.builder()
                .id(IDTWO)
                .name("Project lead")
                .description("Assigns, manages, client interface")
                .build();


        rolesSet.add(developer);
        rolesSet.add(lead);
    }

    @Test
    void findAll() {

        when(roleRepository.findAll()).thenReturn(rolesSet);

        Set<Role> foundRoles = roleService.findAll();

        assertNotNull(foundRoles);
        assertEquals(2, foundRoles.size());
        verify(roleRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        // given setup role

        // when
        when(roleRepository.findById(any())).thenReturn(Optional.of(developer));

        Optional<Role> foundRoleOptional = roleService.findById(developer.getId());

        // expected
        assertTrue(foundRoleOptional.isPresent());
        assertTrue(developer.getId().equals(foundRoleOptional.get().getId()));
        verify(roleRepository, times(1)).findById(any());
    }

    @Test
    void save() {

        when(roleRepository.save(any(Role.class))).thenReturn(developer);

        Role foundRole = roleService.save(developer);

        assertNotNull(foundRole);
        assertTrue(developer.getId().equals(foundRole.getId()));
        verify(roleRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(roleRepository.saveAll(any(Set.class))).thenReturn(rolesSet);

        Set<Role> foundRoles = roleService.saveAll(rolesSet);

        assertNotNull(foundRoles);
        assertEquals(2, foundRoles.size());
        verify(roleRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        roleService.delete(developer);

        verify(roleRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        roleService.deleteById(IDONE);

        verify(roleRepository, times(1)).deleteById(any());
    }
}