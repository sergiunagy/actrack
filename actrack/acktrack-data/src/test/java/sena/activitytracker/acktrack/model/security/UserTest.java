package sena.activitytracker.acktrack.model.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.BaseDomTest;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends BaseDomTest {

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .familyName("Nagy")
                .givenName("Sergiu")
                .build();
    }

    @Test
    void addActivities() {
        // given
        final String TXT1 = "first";
        final String TXT2 = "2nd ";
        Set<Activity> activities = new HashSet<>();
        activities.add(Activity.builder().id(IDONE).description(TXT1).build());
        activities.add(Activity.builder().id(IDTWO).description(TXT2).build());

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

    @Test
    void addWorkpackages() {
        // given
        final String TXT1 = "first";
        final String TXT2 = "2nd ";
        Set<Workpackage> workpackages = new HashSet<>();
        workpackages.add(Workpackage.builder().id(IDONE).description(TXT1).build());
        workpackages.add(Workpackage.builder().id(IDTWO).description(TXT2).build());

        // when
        Set<Workpackage> boundWorkpackages = user.addWorkpackages(workpackages);

        // then
        assertNotNull(user.getWorkpackages());
        assertEquals(2, user.getWorkpackages().size());
        assertTrue(user.getWorkpackages().stream().anyMatch(user -> user.getDescription().equals(TXT1)));
        assertTrue(user.getWorkpackages().stream().anyMatch(user -> user.getDescription().equals(TXT2)));

        assertNotNull(boundWorkpackages);
        Optional<Workpackage> workpackageOptional1 = boundWorkpackages.stream().filter(workpackage1 -> workpackage1.getDescription().equals(TXT1)).findFirst();
        Optional<Workpackage> workpackageOptional2 = boundWorkpackages.stream().filter(workpackage1 -> workpackage1.getDescription().equals(TXT2)).findFirst();
        assertTrue(workpackageOptional1.isPresent());
        assertTrue(workpackageOptional2.isPresent());
        // check directly on objects
        assertTrue(workpackageOptional1.get().getUsers().stream().anyMatch(user1 -> user1 == user));
        assertTrue(workpackageOptional2.get().getUsers().stream().anyMatch(user1 -> user1 == user));

    }

    @Test
    void addWorkpackagesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> user.addWorkpackages(null));
    }

    @Test
    void addWorkpackagesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> user.addWorkpackages(emptySet));
    }

    @Test
    void addWorkpackage() {
        // given
        final String TXT1 = "first";
        Workpackage workpackage = Workpackage.builder().id(IDONE).description(TXT1).build();

        // when
        user.addWorkpackage(workpackage);

        // then
        assertNotNull(user.getWorkpackages());
        assertEquals(1, user.getWorkpackages().size());
        assertTrue(user.getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1.getDescription().equals(TXT1)));

        // check directly on objects
        assertTrue(workpackage.getUsers().stream().anyMatch(user1 -> user1 == user));
    }

}