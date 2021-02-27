package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.dtos.WorkpackageDTO;
import sena.activitytracker.acktrack.mappers.WorkpackageMapper;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.repositories.WorkpackageRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@Service
public class WorkpackageServiceImpl implements WorkpackageService {

    private final WorkpackageRepository workpackageRepository;
    private final WorkpackageMapper workpackageMapper;

    @Override
    public Set<WorkpackageDTO> findAll() {

        Set<WorkpackageDTO> workpackageDTOS = new HashSet<>();

        workpackageDTOS = StreamSupport.stream(workpackageRepository.findAll().spliterator(), false)
                .map(workpackageMapper::toWorkpackageDTO)
                .collect(Collectors.toSet());

        return workpackageDTOS;
    }

    @Override
    public Optional<WorkpackageDTO> findById(@NonNull Long id) {

        Optional<Workpackage> workpackageOptional = workpackageRepository.findById(id);

        return Optional.of(workpackageMapper.toWorkpackageDTO(workpackageOptional.get()));
    }

    @Override
    public WorkpackageDTO save(@NonNull  WorkpackageDTO workpackageDTO) {

        Workpackage savedWorkpackage = workpackageRepository.save(workpackageMapper.toWorkpackage(workpackageDTO));

        return workpackageMapper.toWorkpackageDTO(savedWorkpackage);
    }

    @Override
    public Set<WorkpackageDTO> saveAll(@NonNull Set<WorkpackageDTO> workpackageDTOS) {

        Set<WorkpackageDTO> savedWorkpackageDTOs = new HashSet<>();
        Set<Workpackage> workpackages = new HashSet<>();

        workpackageDTOS.forEach(dto -> workpackages.add(workpackageMapper.toWorkpackage(dto)));

        workpackageRepository.saveAll(workpackages).forEach(wp -> savedWorkpackageDTOs.add(workpackageMapper.toWorkpackageDTO(wp)));

        return savedWorkpackageDTOs;
    }

    @Override
    public void delete(@NonNull WorkpackageDTO workpackageDTO) {

        workpackageRepository.delete(workpackageMapper.toWorkpackage(workpackageDTO));
    }

    @Override
    public void deleteById(@NonNull Long id) {
        workpackageRepository.deleteById(id);
    }
}
