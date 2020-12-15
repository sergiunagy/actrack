package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.repositories.security.UserRepository;
import sena.activitytracker.acktrack.services.security.UserServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends BaseServiceTest{

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    User sergiu, mihai;
    Set<User> userSet =new HashSet<>();

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        sergiu = User.builder()
                .familyName("Nagy")
                .givenName("Sergiu")
                .build();

        mihai = User.builder()
                .familyName("Popa")
                .givenName("Mihai")
                .build();


        userSet.add(sergiu);
        userSet.add(mihai);
    }

    @Test
    void findAll() {

        when(userRepository.findAll()).thenReturn(userSet);

        Set<User> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(userRepository.findById(any())).thenReturn(Optional.of(sergiu));

        Optional<User> foundUserOptional = userService.findById(sergiu.getId());

        assertTrue(foundUserOptional.isPresent());
        assertTrue(sergiu.getId().equals(foundUserOptional.get().getId()));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void save() {

        when(userRepository.save(any(User.class))).thenReturn(sergiu);

        User foundUser = userService.save(sergiu);

        assertNotNull(foundUser);
        assertTrue(sergiu.getId().equals(foundUser.getId()));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(userRepository.saveAll(any(Set.class))).thenReturn(userSet);

        Set<User> foundUsers = userService.saveAll(userSet);

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        verify(userRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        userService.delete(sergiu);

        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        userService.deleteById(IDONE);

        verify(userRepository, times(1)).deleteById(any());
    }
}