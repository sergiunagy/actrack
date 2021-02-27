package sena.activitytracker.acktrack.services.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.dtos.UserDTO;
import sena.activitytracker.acktrack.mappers.UserMapper;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.repositories.security.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Set<UserDTO> findAll() {

        Set<UserDTO> users = new HashSet<>();

        userRepository.findAll().forEach(user -> users.add(userMapper.toUserDTO(user)));

        return users;
    }

    @Override
    public Optional<UserDTO> findById(Long id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent())
            return Optional.ofNullable(userMapper.toUserDTO(user.get()));
        else
            return Optional.empty();
    }

    @Override
    public UserDTO save(UserDTO userDTO) {

       return  userMapper.toUserDTO(userRepository.save(userMapper.toUser(userDTO)));
    }

    @Override
    public Set<UserDTO> saveAll(Set<UserDTO> userDTOs) {

        Set<UserDTO> retUserDTOs = new HashSet<>();
        Set<User> users = new HashSet<>();

        userDTOs.forEach(userDTO -> users.add(userMapper.toUser(userDTO)));

        userRepository.saveAll(users).forEach(user -> retUserDTOs.add(userMapper.toUserDTO(user)));

        return retUserDTOs;
    }

    @Override
    public void delete(UserDTO userDTO) {

        userRepository.delete(userMapper.toUser(userDTO));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
