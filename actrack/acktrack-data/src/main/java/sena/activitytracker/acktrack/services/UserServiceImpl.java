package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.User;
import sena.activitytracker.acktrack.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

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
    public User findById(Long id) {

        return userRepository.findById(id).orElse(null);
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
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
