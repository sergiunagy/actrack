package sena.activitytracker.acktrack.services.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.security.Authority;
import sena.activitytracker.acktrack.repositories.security.AuthorityRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Builder
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityRepository authorityRepository;

    @Override
    public Set<Authority> findAll() {

        Set<Authority> authorities = new HashSet<>();

        authorityRepository.findAll().forEach(authorities::add);

        return authorities;
    }

    @Override
    public Optional<Authority> findById(UUID uuid) {

        return authorityRepository.findById(uuid);
    }

    @Override
    public Authority save(Authority authority) {

        return authorityRepository.save(authority);
    }

    @Override
    public Set<Authority> saveAll(Set<Authority> authoritySet) {

        Set<Authority> authorities = new HashSet<>();

        authorityRepository.saveAll(authoritySet).forEach(authorities::add);

        return authorities;
    }

    @Override
    public void delete(Authority authority) {

        authorityRepository.delete(authority);
    }

    @Override
    public void deleteById(UUID uuid) {

        authorityRepository.deleteById(uuid);

    }
}
