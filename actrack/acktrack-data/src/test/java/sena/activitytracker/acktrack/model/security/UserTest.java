package sena.activitytracker.acktrack.model.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.BaseDomTest;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends BaseDomTest {

    User userAdmin, userDev;
    Role admin, developer;
    Authority userCreate, userRead, userUpdate, userDelete, activityCreate, activityRead, activityUpdate, activityDelete;

    @BeforeEach
    void setUp() {

        userCreate = Authority.builder().permission("user.create").build();
        userRead = Authority.builder().permission("user.read").build();
        userUpdate = Authority.builder().permission("user.update").build();
        userDelete = Authority.builder().permission("user.delete").build();
        activityCreate = Authority.builder().permission("activity.create").build();
        activityRead = Authority.builder().permission("activity.read").build();
        activityUpdate = Authority.builder().permission("activity.update").build();
        activityDelete = Authority.builder().permission("activity.delete").build();

        admin = Role.builder()
                .name("administrator user")
                .build();
        developer = Role.builder()
                .name("developer user")
                .build();

        admin.setAuthorities(new HashSet<>(Set.of(userCreate, userRead, userUpdate, userDelete, activityCreate, activityRead, activityUpdate, activityDelete)));
        developer.setAuthorities(new HashSet<>(Set.of(activityCreate, activityRead, activityUpdate, activityDelete)));

        userAdmin = User.builder()
                .role(admin)
                .familyName("Admin")
                .givenName("Test")
                .build();

        userDev = User.builder()
                .role(developer)
                .familyName("Developer")
                .givenName("Test")
                .build();
    }

    @Test
    void getAuthoritiesTest(){
         /* given : admin user in setup */

        /* when: get user authorities*/
        Set<GrantedAuthority> authorities = userAdmin.getAuthorities();

        assertNotNull(authorities);
        assertEquals(8, authorities.stream().count());

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
        Set<Activity> boundActivities = userAdmin.addActivities(activities);

        // then
        assertNotNull(userAdmin.getActivities());
        assertEquals(2, userAdmin.getActivities().size());
        assertTrue(userAdmin.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT1)));
        assertTrue(userAdmin.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT2)));

        assertNotNull(boundActivities);
        Optional<Activity> activityOptional1 = boundActivities.stream().filter(activity -> activity.getDescription().equals(TXT1)).findFirst();
        Optional<Activity> activityOptional2 = boundActivities.stream().filter(activity -> activity.getDescription().equals(TXT2)).findFirst();
        assertTrue(activityOptional1.isPresent());
        assertTrue(activityOptional2.isPresent());

        // check directly on objects
        assertTrue(activityOptional1.get().getUser() == userAdmin);
        assertTrue(activityOptional2.get().getUser() == userAdmin);
    }

    @Test
    void addActivitiesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> userAdmin.addActivities(null));
    }

    @Test
    void addActivitiesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> userAdmin.addActivities(emptySet));
    }

    @Test
    void addActivityNull() {
        Assertions.assertThrows(RuntimeException.class, ()-> userAdmin.addActivity(null));
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
        Set<Workpackage> boundWorkpackages = userAdmin.addWorkpackages(workpackages);

        // then
        assertNotNull(userAdmin.getWorkpackages());
        assertEquals(2, userAdmin.getWorkpackages().size());
        assertTrue(userAdmin.getWorkpackages().stream().anyMatch(wp -> wp.getDescription().equals(TXT1)));
        assertTrue(userAdmin.getWorkpackages().stream().anyMatch(wp -> wp.getDescription().equals(TXT2)));

        assertNotNull(boundWorkpackages);
        Optional<Workpackage> workpackageOptional1 = boundWorkpackages.stream().filter(workpackage1 -> workpackage1.getDescription().equals(TXT1)).findFirst();
        Optional<Workpackage> workpackageOptional2 = boundWorkpackages.stream().filter(workpackage1 -> workpackage1.getDescription().equals(TXT2)).findFirst();
        assertTrue(workpackageOptional1.isPresent());
        assertTrue(workpackageOptional2.isPresent());
        // check directly on objects
        assertTrue(workpackageOptional1.get().getUsers().stream().anyMatch(user1 -> user1 == userAdmin));
        assertTrue(workpackageOptional2.get().getUsers().stream().anyMatch(user1 -> user1 == userAdmin));

    }

    @Test
    void addWorkpackagesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> userAdmin.addWorkpackages(null));
    }

    @Test
    void addWorkpackagesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> userAdmin.addWorkpackages(emptySet));
    }

    @Test
    void addWorkpackage() {
        // given
        final String TXT1 = "first";
        Workpackage workpackage = Workpackage.builder().id(IDONE).description(TXT1).build();

        // when
        userAdmin.addWorkpackage(workpackage);

        // then
        assertNotNull(userAdmin.getWorkpackages());
        assertEquals(1, userAdmin.getWorkpackages().size());
        assertTrue(userAdmin.getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1.getDescription().equals(TXT1)));

        // check directly on objects
        assertTrue(workpackage.getUsers().stream().anyMatch(user1 -> user1 == userAdmin));
    }

}