package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .familyName("Nagy")
                .givenName("Sergiu")
                .uid("u1")
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
        Set<Activity> boundActivities = user.addActivities(activities);

        // then
        assertNotNull(user.getActivities());
        assertEquals(2, user.getActivities().size());
        assertTrue(user.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT1)));
        assertTrue(user.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT2)));

        assertNotNull(boundActivities);
        Optional<Activity> activityOptional1 = boundActivities.stream().filter(activity -> activity.getDescription().equals(TXT1)).findFirst();
        Optional<Activity> activityOptional2 = boundActivities.stream().filter(activity -> activity.getDescription().equals(TXT2)).findFirst();
        assertTrue(activityOptional1.isPresent());
        assertTrue(activityOptional2.isPresent());

        // check directly on objects
        assertTrue(activityOptional1.get().getUser() == user);
        assertTrue(activityOptional2.get().getUser() == user);
    }

    @Test
    void addActivitiesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> user.addActivities(null));
    }

    @Test
    void addActivitiesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> user.addActivities(emptySet));
    }

    @Test
    void addActivityNull() {
        Assertions.assertThrows(RuntimeException.class, ()-> user.addActivity(null));
    }
}