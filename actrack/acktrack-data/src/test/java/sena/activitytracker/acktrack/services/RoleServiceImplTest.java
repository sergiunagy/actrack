package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.model.Role;
import sena.activitytracker.acktrack.repositories.RoleRepository;

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
class RoleServiceImplTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleService;

    Role developer, lead;
    Set<Role> issueSet =new HashSet<>();

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        developer = Role.builder()
                .id(1L)
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();

        lead = Role.builder()
                .id(2L)
                .name("Project lead")
                .description("Assigns, manages, client interface")
                .build();


        issueSet.add(developer);
        issueSet.add(lead);
    }

    @Test
    void findAll() {

        when(roleRepository.findAll()).thenReturn(issueSet);

        Set<Role> foundRoles = roleService.findAll();

        assertNotNull(foundRoles);
        assertEquals(2, foundRoles.size());
        verify(roleRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(developer));

        Role foundRole = roleService.findById(developer.getId());

        assertNotNull(foundRole);
        assertEquals(1, foundRole.getId());
        verify(roleRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {

        when(roleRepository.save(any(Role.class))).thenReturn(developer);

        Role foundRole = roleService.save(developer);

        assertNotNull(foundRole);
        assertEquals(1, foundRole.getId());
        verify(roleRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(roleRepository.saveAll(any(Set.class))).thenReturn(issueSet);

        Set<Role> foundRoles = roleService.saveAll(issueSet);

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

        roleService.deleteById(1L);

        verify(roleRepository, times(1)).deleteById(anyLong());
    }
}