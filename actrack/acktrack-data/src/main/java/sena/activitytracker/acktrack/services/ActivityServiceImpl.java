package sena.activitytracker.acktrack.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Override
    public Set<Activity> findAll() {
        Set<Activity> issues = new HashSet<>();
        activityRepository.findAll().forEach(issues::add);

        return issues;
    }

    @Override
    public Activity findById(UUID id) {

        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public Activity save(Activity issue) {

        return activityRepository.save(issue);
    }

    @Override
    public Set<Activity> saveAll(Set<Activity> issues) {
        Set<Activity> retActivitys = new HashSet<>();
        activityRepository.saveAll(issues).forEach(retActivitys::add);

        return retActivitys;
    }

    @Override
    public void delete(Activity issue) {

        activityRepository.delete(issue);
    }

    @Override
    public void deleteById(UUID id) {
        activityRepository.deleteById(id);
    }

    @Override
    public Set<Activity> findAllActivitiesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return activityRepository.findAllByDateBetween(startDate, endDate);
    }

    public Set<Activity> findAllByDate(LocalDate date){

        return activityRepository.findAllByDate(date);
    }
}
