package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.dtos.UserDTO;
import sena.activitytracker.acktrack.mappers.UserMapper;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.repositories.security.UserRepository;
import sena.activitytracker.acktrack.services.security.UserServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends BaseServiceTest{

    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    UserDTO sergiuDTO, mihaiDTO;
    User sergiu, mihai;
    Set<UserDTO> userDTOSet =new HashSet<>();
    Set<User> userSet =new HashSet<>();

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        sergiuDTO = UserDTO.builder()
                .username("sena")
                .familyName("Nagy")
                .givenName("Sergiu")
                .build();

        mihaiDTO = UserDTO.builder()
                .username("mipo")
                .familyName("Popa")
                .givenName("Mihai")
                .build();

        userDTOSet = Set.of(sergiuDTO, mihaiDTO);

        /* Create the mapper conversions too - simplify test code*/
        sergiu = User.builder()
                .id(IDONE)
                .username("sena")
                .familyName("Nagy")
                .givenName("Sergiu")
                .build();

        mihai = User.builder()
                .id(IDTWO)
                .username("mipo")
                .familyName("Popa")
                .givenName("Mihai")
                .build();

        userSet = Set.of(sergiu, mihai);

    }

    @Test
    void findAll() {

        when(userRepository.findAll()).thenReturn(userSet);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(sergiuDTO);

        Set<UserDTO> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).toUserDTO(any(User.class));
    }

    @Test
    void findById() {
        when(userRepository.findById(any())).thenReturn(Optional.of(sergiu));
        when(userMapper.toUserDTO(any(User.class))).thenReturn(sergiuDTO);

        Optional<UserDTO> foundUserOptional = userService.findById(sergiu.getId());

        assertTrue(foundUserOptional.isPresent());
        verify(userRepository, times(1)).findById(any());
        verify(userMapper, times(1)).toUserDTO(any(User.class));
    }

    @Test
    void findByIdNeg() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());  /* Id not found*/

        Optional<UserDTO> foundUserOptional = userService.findById(sergiu.getId());

        assertFalse(foundUserOptional.isPresent());
        verify(userRepository, times(1)).findById(any());
        verify(userMapper, times(0)).toUserDTO(any());
    }

    @Test
    void save() {

        when(userRepository.save(any(User.class))).thenReturn(sergiu);
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(sergiu);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(sergiuDTO);

        UserDTO savedUser = userService.save(sergiuDTO);

        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any());
        verify(userMapper, times(1)).toUser(any());
        verify(userMapper, times(1)).toUserDTO(any());
    }

    @Test
    void saveAll() {
        when(userRepository.saveAll(any(Set.class))).thenReturn(userSet);
        when(userMapper.toUser(any(UserDTO.class))).thenReturn(sergiu);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(sergiuDTO);

        Set<UserDTO> savedUsers = userService.saveAll(userDTOSet);

        assertNotNull(savedUsers);
        verify(userRepository, times(1)).saveAll(any());
        verify(userMapper, times(2)).toUser(any());
        verify(userMapper, times(2)).toUserDTO(any());
    }

    @Test
    void delete() {

        when(userMapper.toUser(any(UserDTO.class))).thenReturn(sergiu);

        userService.delete(sergiuDTO);

        verify(userRepository, times(1)).delete(any(User.class));
        verify(userMapper, times(1)).toUser(any());
    }

    @Test
    void deleteById() {

        userService.deleteById(IDONE);

        verify(userRepository, times(1)).deleteById(any());
    }
}