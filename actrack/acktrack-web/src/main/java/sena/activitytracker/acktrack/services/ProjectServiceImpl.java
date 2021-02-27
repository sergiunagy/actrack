package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.mappers.ProjectMapper;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.repositories.ProjectRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Set<ProjectDTO> findAll() {

        Set<ProjectDTO> projectDTOs = new HashSet<>();

        projectRepository.findAll().forEach(project -> projectDTOs.add(projectMapper.toProjectDTO(project)));


//        projectDTOs = StreamSupport.stream(projectRepository.findAll().spliterator(), false)
//                .map(projectMapper::toProjectDTO)
//                .collect(Collectors.toSet());

        return projectDTOs;
    }


    @Override
    public Optional<ProjectDTO> findById(@NonNull Long id) {

        Optional<Project> projectOptional = projectRepository.findById(id);

        return Optional.of(projectMapper.toProjectDTO(projectOptional.get()));
    }

    @Override
    public ProjectDTO save(@NonNull ProjectDTO projectDTO) {

        Project savedProject = projectRepository.save(projectMapper.toProject(projectDTO));

        return projectMapper.toProjectDTO(savedProject);
    }

    @Override
    public Set<ProjectDTO> saveAll(@NonNull Set<ProjectDTO> projectDTOs) {

        Set<Project> projects = new HashSet<>();
        Set<ProjectDTO> savedProjectDTOs = new HashSet<>();

        projectDTOs.forEach(projectDTO -> projects.add(projectMapper.toProject(projectDTO)));

        projectRepository.saveAll(projects).forEach(project -> savedProjectDTOs.add(projectMapper.toProjectDTO(project)));

        return savedProjectDTOs;
    }

    @Override
    public void delete(@NonNull ProjectDTO projectDTO) {

        projectRepository.delete(projectMapper.toProject(projectDTO));
    }

    @Override
    public void deleteById(@NonNull Long id) {
        projectRepository.deleteById(id);
    }
}
