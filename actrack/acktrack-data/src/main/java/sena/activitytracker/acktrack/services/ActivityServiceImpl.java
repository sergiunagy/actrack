package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Override
    public Set<Activity> findAll() {
        Set<Activity> activitySet = new HashSet<>();
        activityRepository.findAll().forEach(activitySet::add);
        return activitySet;
    }

    @Override
    public Activity findById(Long aLong) {
        return null;
    }

    @Override
    public Activity save(Activity obj) {
        return null;
    }

    @Override
    public Set<Activity> saveAll(Set<Activity> entities) {

        return StreamSupport.stream(activityRepository.saveAll(entities).spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public void delete(Activity obj) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Set<Activity> findAllActivitiesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return activityRepository.findAllByDateBetween(startDate, endDate);
    }
}
