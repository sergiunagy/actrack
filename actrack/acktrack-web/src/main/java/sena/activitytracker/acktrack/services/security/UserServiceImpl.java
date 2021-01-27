package sena.activitytracker.acktrack.services.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    @Override
    public Set<User> findAll() {

        Set<User> issues = new HashSet<>();

        userRepository.findAll().forEach(issues::add);

        return issues;
    }

    @Override
    public Optional<User> findById(UUID id) {

        return userRepository.findById(id);
    }

    @Override
    public User save(User issue) {

        return userRepository.save(issue);
    }

    @Override
    public Set<User> saveAll(Set<User> issues) {

        Set<User> retUsers = new HashSet<>();

        userRepository.saveAll(issues).forEach(retUsers::add);

        return retUsers;
    }

    @Override
    public void delete(User issue) {

        userRepository.delete(issue);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

}
