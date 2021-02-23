package sena.activitytracker.acktrack.integration.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sena.activitytracker.acktrack.bootstrap.BootstrapData_obsolete;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ActivitiesServiceIT {

    @Autowired
    ActivityRepository activityService;


    @Test
    void testLoadActivitiesBetweenDates() {
        int N = 121; /*120 bootstraped activities plus one in the setup*/

        Set<Activity> databaseActivities = new HashSet<>();
        activityService.findAll().forEach(databaseActivities::add);
        assertEquals(N, databaseActivities.size());
        Set<Activity> datedActivities = activityService.findAllByDateBetween(LocalDate.now().minusDays(5), LocalDate.now());

        System.out.println(datedActivities);
    }


    @Test
    void saveLargeTextActivity() {

        String expectedText = BootstrapData_obsolete.TEXT500 + "5A5A";

        LocalDate date = LocalDate.of(2020, 8, 14);
        Activity largeTextActivity = Activity.builder()
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(date)
                .description(BootstrapData_obsolete.TEXT500+"5A5A")
                .isExported(false)
                .build();

        activityService.save(largeTextActivity);

        Set<Activity> found = activityService.findAllByDate(date);

        assertNotNull(found);
        assertEquals(1, found.stream().count());
        assertTrue(expectedText.equals(found.stream().findAny().get().getDescription()));
    }

}
