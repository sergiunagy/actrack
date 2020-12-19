package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.ProjectUserRole;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.repositories.WorkpackageRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class WorkpackageServiceImpl implements WorkpackageService {

    private final WorkpackageRepository workpackageRepository;

    @Override
    public Set<Workpackage> findAll() {

        Set<Workpackage> workpackages = new HashSet<>();

        workpackageRepository.findAll().forEach(workpackages::add);

        return workpackages;
    }

    @Override
    public Optional<Workpackage> findById(UUID id) {

        return workpackageRepository.findById(id);
    }

    @Override
    public Workpackage save(Workpackage workpackage) {

        return workpackageRepository.save(workpackage);
    }

    @Override
    public Set<Workpackage> saveAll(Set<Workpackage> workpackages) {

        Set<Workpackage> retWorkpackages = new HashSet<>();

        workpackageRepository.saveAll(workpackages).forEach(retWorkpackages::add);

        return retWorkpackages;
    }

    @Override
    public void delete(Workpackage workpackage) {

        workpackageRepository.delete(workpackage);
    }

    @Override
    public void deleteById(UUID id) {
        workpackageRepository.deleteById(id);
    }
}
