package sena.activitytracker.acktrack.integration.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sena.activitytracker.acktrack.bootstrap.bootstrapData;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;
import sena.activitytracker.acktrack.services.ActivityService;
import sena.activitytracker.acktrack.services.ActivityServiceImpl;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ActivitiesServiceIT {

    @Autowired
    ActivityRepository activityService;


    @Test
    void testLoadActivitiesBetweenDates() {
        int TEN = 10;

        Set<Activity> databaseActivities = new HashSet<>();
        activityService.findAll().forEach(databaseActivities::add);
        assertEquals(10, databaseActivities.size());
        Set<Activity> datedActivities = activityService.findAllByDateBetween(LocalDate.now().minusDays(5), LocalDate.now());

        System.out.println(datedActivities);
    }


    @Test
    void saveLargeTextActivity() {

        String expectedText = bootstrapData.TEXT500 + "5A5A";

        LocalDate date = LocalDate.of(2020, 8, 14);
        Activity largeTextActivity = Activity.builder()
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(date)
                .description(bootstrapData.TEXT500+"5A5A")
                .isExported(false)
                .build();

        activityService.save(largeTextActivity);

        Set<Activity> found = activityService.findAllByDate(date);

        assertNotNull(found);
        assertEquals(1, found.stream().count());
        assertTrue(expectedText.equals(found.stream().findAny().get().getDescription()));
    }

}
