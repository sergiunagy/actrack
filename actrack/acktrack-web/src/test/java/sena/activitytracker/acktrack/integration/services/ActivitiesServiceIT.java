package sena.activitytracker.acktrack.integration.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;
import sena.activitytracker.acktrack.services.ActivityService;
import sena.activitytracker.acktrack.services.ActivityServiceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ActivitiesServiceIT {

    @Autowired
    ActivityRepository activityService;


//    @Test
//    void testRepositorySave() {
//
//        int TEN = 10;
//
//        saveNActivitiesToRepository(TEN);
//
//        Set<Activity> databaseActivities = new HashSet<>();
//        activityService.findAll().forEach(databaseActivities::add);
//        assertEquals(10, databaseActivities.size());
//    }


    @Test
    void testLoadActivitiesBetweenDates(){
        int TEN = 10;

        saveNActivitiesToRepository(TEN);

        Set<Activity> databaseActivities = new HashSet<>();
        activityService.findAll().forEach(databaseActivities::add);
        assertEquals(10, databaseActivities.size());
        Set<Activity> datedActivities = activityService.findAllByDateBetween(LocalDate.now().minusDays(5), LocalDate.now() );

        System.out.println(datedActivities);
    }


    void saveNActivitiesToRepository(int n) {

        int MAXRANGE = n; //days

        Set<Activity> activities = new HashSet<>();

        IntStream.range(0, MAXRANGE).parallel().forEach(
                idx -> {
                    activities.add(Activity.builder()
                            .date(LocalDate.now().minusDays((MAXRANGE - idx)))
                            .description("activity" + idx)
                            .build());
                }
        );
        activityService.saveAll(activities);
    }

}
