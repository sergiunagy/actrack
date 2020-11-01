package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WorkpackageTest {

    Workpackage workpackage;

    @BeforeEach
    void setUp() {
        workpackage = Workpackage.builder()
                .id(1L)
                .name("fix quality")
                .description("fix quality on alpha")
                .build();
    }

    @Test
    void addActivities() {
        // given
        final String TXT1 = "first";
        final String TXT2 = "2nd ";
        Set<Activity> activities = new HashSet<>();
        activities.add(Activity.builder().id(1L).description(TXT1).build());
        activities.add(Activity.builder().id(2L).description(TXT2).build());

        // when
        Set<Activity> boundActivities = workpackage.addActivities(activities);

        // then
        assertNotNull(workpackage.getActivities());
        assertEquals(2, workpackage.getActivities().size());
        assertTrue(workpackage.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT1)));
        assertTrue(workpackage.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT2)));

        assertNotNull(boundActivities);
        Optional<Activity> activityOptional1 = boundActivities.stream().filter(activity -> activity.getDescription().equals(TXT1)).findFirst();
        Optional<Activity> activityOptional2 = boundActivities.stream().filter(activity -> activity.getDescription().equals(TXT2)).findFirst();
        assertTrue(activityOptional1.isPresent());
        assertTrue(activityOptional2.isPresent());

        // check directly on objects
        assertTrue(activityOptional1.get().getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1 == workpackage));
        assertTrue(activityOptional2.get().getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1 == workpackage));
    }

    @Test
    void addActivitiesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> workpackage.addActivities(null));
    }

    @Test
    void addActivitiesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> workpackage.addActivities(emptySet));
    }

}
