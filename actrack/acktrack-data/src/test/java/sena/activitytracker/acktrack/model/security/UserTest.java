package sena.activitytracker.acktrack.model.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import sena.activitytracker.acktrack.model.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends BaseDomTest {

    User userAdmin, userDev;
    Role roleAdmin, roleDev;
    Authority authUserCreate, authUserRead, authUserUpdate, authUserDelete, authActivityCreate, authActivityRead, authActivityUpdate, authActivityDelete;

    @BeforeEach
    void setUp() {

        /*Create dummy authorities*/
        authUserCreate = Authority.builder().permission("user.create").build();
        authUserRead = Authority.builder().permission("user.read").build();
        authUserUpdate = Authority.builder().permission("user.update").build();
        authUserDelete = Authority.builder().permission("user.delete").build();
        authActivityCreate = Authority.builder().permission("activity.create").build();
        authActivityRead = Authority.builder().permission("activity.read").build();
        authActivityUpdate = Authority.builder().permission("activity.update").build();
        authActivityDelete = Authority.builder().permission("activity.delete").build();

        /*Create dummy roles*/
        roleAdmin = Role.builder()
                .name("administrator user")
                .build();
        roleDev = Role.builder()
                .name("developer user")
                .build();

        /*Associate roles and authorities*/
        roleAdmin.setAuthorities(new HashSet<>(Set.of(authUserCreate, authUserRead, authUserUpdate, authUserDelete,
                                                    authActivityCreate, authActivityRead, authActivityUpdate, authActivityDelete)));
        roleDev.setAuthorities(new HashSet<>(Set.of(authActivityCreate, authActivityRead, authActivityUpdate, authActivityDelete)));

        /*create the users*/
        userAdmin = User.builder()
                .familyName("Admin")
                .givenName("Test")
                .build();

        userDev = User.builder()
                .familyName("Developer")
                .givenName("Test")
                .build();

        /*Associate roles and users*/
        userAdmin.addRole(roleAdmin);
        userDev.addRole(roleDev);

    }

    @Test
    void getIssuesTest() {

        // given
        /*3 issues with 2 wps and 3 activities*/
        Issue i1 =  Issue.builder().shortName("dummy issue one").build();
        Issue i2 =  Issue.builder().shortName("dummy issue two").build();
        Issue i3 =  Issue.builder().shortName("dummy issue two").build();

        Activity a1 = Activity.builder().description("dummy act one").build();
        Activity a2 = Activity.builder().description("dummy act two").build();
        Activity a3 = Activity.builder().description("dummy act three").build();

        Workpackage w1 = Workpackage.builder().description("dummy wp one").build();
        Workpackage w2 = Workpackage.builder().description("dummy wp two").build();

        w1.addActivities(Set.of(a1,a2));
        w2.addActivity(a3);

        i1.addWorkpackage(w1);
        i2.addWorkpackage(w2);
        i3.addWorkpackage(w1);

        userDev.addActivities(Set.of(a1,a2,a3));

        // when
        Set<Issue> foundIssues = userDev.getIssues();

        //then
        assertNotNull(foundIssues);
        assertEquals(3, foundIssues.size());
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

        Set<Activity> foundActivities = userAdmin.addActivities(new HashSet());

        assertTrue(foundActivities.isEmpty());
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
        workpackages.add(Workpackage.builder().description(TXT1).build());
        workpackages.add(Workpackage.builder().description(TXT2).build());

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

        Set<Workpackage> foundWps = userAdmin.addWorkpackages(new HashSet());

        assertTrue(foundWps.isEmpty());
    }

    @Test
    void addWorkpackage() {
        // given
        final String TXT1 = "first";
        Workpackage workpackage = Workpackage.builder().description(TXT1).build();

        // when
        userAdmin.addWorkpackage(workpackage);

        // then
        assertNotNull(userAdmin.getWorkpackages());
        assertEquals(1, userAdmin.getWorkpackages().size());
        assertTrue(userAdmin.getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1.getDescription().equals(TXT1)));

        // check directly on objects
        assertTrue(workpackage.getUsers().stream().anyMatch(user1 -> user1 == userAdmin));
    }

    @Test
    void addProjectTest(){
        // given
        Project p1 = Project.builder().name("p1").build();

        // when
        userDev.addProject(p1);

        // then
        Set<Project> foundProjects = userDev.getProjects();
        assertNotNull(foundProjects);
        assertEquals(1, foundProjects.size());
        assertEquals("p1", foundProjects.stream().findAny().get().getName());
    }

    @Test
    void addProjectsTest(){
        // given
        Set<Project> projects = new HashSet<>();
        projects.add(Project.builder().name("p1").build());
        projects.add(Project.builder().name("p2").build());

        //when
        userDev.addProjects(projects);

        //then
        Set<Project> foundProjects = userDev.getProjects();
        assertNotNull(foundProjects);
        assertEquals(2, foundProjects.size());
    }

    @Test
    void addProjectsNullTest() {

        Assertions.assertThrows(RuntimeException.class, ()-> userDev.addProjects(null));
    }

    @Test
    void addProjectsEmptyTest() {

        Set<Project> foundProjects = userDev.addProjects(new HashSet());

        assertTrue(foundProjects.isEmpty());
    }
}