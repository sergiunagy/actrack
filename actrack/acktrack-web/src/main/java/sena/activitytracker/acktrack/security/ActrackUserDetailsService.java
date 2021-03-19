package sena.activitytracker.acktrack.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.repositories.security.UserRepository;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActrackUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional /* because we will have 2 separate accesses to the User DAO*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("For user: " + username);
        });

        return null;
    }
}
