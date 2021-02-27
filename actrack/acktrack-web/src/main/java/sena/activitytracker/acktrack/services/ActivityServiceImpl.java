package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.mappers.ActivityMapper;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public Set<ActivityDTO> listAllActivities(){

        Set<ActivityDTO> activityDTOS = new HashSet<>();
        activityDTOS = StreamSupport.stream(activityRepository.findAll().spliterator(), false)
                .map(activityMapper::toActivityDTO)
                .collect(Collectors.toSet());

        return activityDTOS;
    }

    @Override
    public Set<Activity> findAll() {

        Set<Activity> activities = new HashSet<>();
        activityRepository.findAll().forEach(activities::add);

        return activities;
    }

    @Override
    public Optional<Activity> findById(Long id) {

        return activityRepository.findById(id);
    }

    @Override
    public Activity save(Activity issue) {

        return activityRepository.save(issue);
    }

    @Override
    public Set<Activity> saveAll(Set<Activity> issues) {
        Set<Activity> retActivities = new HashSet<>();

        activityRepository.saveAll(issues).forEach(retActivities::add);

        return retActivities;
    }

    @Override
    public void delete(Activity issue) {

        activityRepository.delete(issue);
    }

    @Override
    public void deleteById(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public Set<Activity> findAllActivitiesBetweenDates(LocalDate startDate, LocalDate endDate) {
        Set<Activity> foundActivities = new HashSet<>();

        activityRepository.findAllByDateBetween(startDate, endDate).forEach(foundActivities::add);

        return foundActivities;
    }

    public Set<Activity> findAllByDate(LocalDate date){

        Set<Activity> foundActivities = new HashSet<>();

        activityRepository.findAllByDate(date).forEach(foundActivities::add);

        return foundActivities;
    }
}
