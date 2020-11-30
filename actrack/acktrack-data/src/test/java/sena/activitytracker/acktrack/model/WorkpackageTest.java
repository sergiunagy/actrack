package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sena.activitytracker.acktrack.model.security.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WorkpackageTest extends BaseDomTest {

    Workpackage workpackage;

    @BeforeEach
    void setUp() {
        workpackage = Workpackage.builder()
                .id(IDONE)
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
        activities.add(Activity.builder().id(IDONE).description(TXT1).build());
        activities.add(Activity.builder().id(IDONE).description(TXT2).build());

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

    @Test
    void addUsers() {
        // given
        final String TXT1 = "first";
        final String TXT2 = "2nd ";
        Set<User> users = new HashSet<>();
        users.add(User.builder().id(IDONE).givenName(TXT1).build());
        users.add(User.builder().id(IDONE).givenName(TXT2).build());

        //when
        Set<User> boundUsers = workpackage.addUsers(users);

        //then
        assertNotNull(workpackage.getUsers());
        assertEquals(2, workpackage.getUsers().size());
        assertTrue(workpackage.getUsers().stream().anyMatch(user -> user.getGivenName().equals(TXT1)));
        assertTrue(workpackage.getUsers().stream().anyMatch(user -> user.getGivenName().equals(TXT2)));

        assertNotNull(boundUsers);
        Optional<User> user1 = boundUsers.stream().filter(user -> user.getGivenName().equals(TXT1)).findFirst();
        Optional<User> user2 = boundUsers.stream().filter(user -> user.getGivenName().equals(TXT2)).findFirst();
        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        // check directly on objects
        assertTrue(user1.get().getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1 == workpackage));
        assertTrue(user2.get().getWorkpackages().stream().anyMatch(workpackage2 -> workpackage2 == workpackage));
    }


    @Test
    void addUsersNull() {

        Assertions.assertThrows(RuntimeException.class, () -> workpackage.addUsers(null));
    }

    @Test
    void addUsersEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, () -> workpackage.addUsers(emptySet));
    }


    @Test
    void addUser() {
        final String TXT1 = "first";
        // given
        final String ROLE_DESC1 = "first role";
        User user = User.builder().id(IDONE).givenName(TXT1).build();

        //when
        workpackage.addUser(user);

        //then
        assertNotNull(workpackage.getUsers());
        assertEquals(1, workpackage.getUsers().size());
        assertTrue(workpackage.getUsers().stream().anyMatch(user1 -> user1.getGivenName().equals(TXT1)));

        // reverse linkage
        assertTrue(user.getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1 == workpackage));
    }

}
