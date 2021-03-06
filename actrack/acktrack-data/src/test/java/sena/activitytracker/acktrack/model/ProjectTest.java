package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest extends BaseDomTest {

    Project project;
    User sergiu, mihai;
    Role developer;
    Set<User> users;
    Set<Role> roles;

    @BeforeEach
    void setUp() {

        sergiu= User.builder().givenName("Sergiu").username("sena").build();
        mihai = User.builder().givenName("Mihai").username("mipo").build();
        users = new HashSet<>();
        users.add(sergiu);
        users.add(mihai);

        developer = Role.builder().name("developer").build();
        roles = new HashSet<>();
        roles.add(developer);

        project = Project.builder()
                .id(Long.valueOf(1))
                .name("alpha")
                .description("dummy alpha")
                .mainLocation("Alpha location")
                .plannedEndDate(LocalDate.of(2018,10,20))
                .actualEndDate(LocalDate.of(2020,10,20))
                .plannedSopDate(LocalDate.of(2019,6,20))
                .plannedEndDate(LocalDate.of(2020,10,1))
                .customerName("Alpha Daimler")
                .customerId("12s42")
                .productLine("alpha moto")
                .active(true)
                .build();
    }


    @Test
    void addIssues() {

        // given
        final String ISSUE_TEXT1 = "first issue";
        final String ISSUE_TEXT2 = "2nd issue";
        Set<Issue> issues = new HashSet<>();
        issues.add(Issue.builder().id(IDONE).shortName(ISSUE_TEXT1).build());
        issues.add(Issue.builder().id(IDTWO).shortName(ISSUE_TEXT2).build());

        //when
        Set<Issue> boundIssues = project.addIssues(issues);

        //then
        assertNotNull(project.getIssues());
        assertEquals(2, project.getIssues().size());
        assertTrue(project.getIssues().stream().anyMatch(issue -> issue.getShortName().equals(ISSUE_TEXT1)));
        assertTrue(project.getIssues().stream().anyMatch(issue -> issue.getShortName().equals(ISSUE_TEXT2)));

        /* test reverse linkage*/
        assertNotNull(boundIssues);
        Optional<Issue> issue1 = boundIssues.stream().filter(issue -> issue.getShortName().equals(ISSUE_TEXT1)).findFirst();
        Optional<Issue> issue2 = boundIssues.stream().filter(issue -> issue.getShortName().equals(ISSUE_TEXT2)).findFirst();
        assertTrue(issue1.isPresent());
        assertTrue(issue2.isPresent());
        assertEquals(project, issue1.get().getProject());
        assertEquals(project, issue2.get().getProject());

    }

    @Test
    void addIssuesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> project.addIssues(null));
    }

    @Test
    void addIssuesEmpty() {

        Set emptySet = new HashSet();
        assertTrue(project.addIssues(emptySet).isEmpty());
    }


    @Test
    void addIssue() {
        // given
        final String ISSUE_TEXT1 = "first issue";
        Issue issue = Issue.builder().id(IDONE).shortName(ISSUE_TEXT1).build();

        //when
        project.addIssue(issue);

        //then
        assertNotNull(project.getIssues());
        assertEquals(1, project.getIssues().size());
        assertTrue(project.getIssues().stream().anyMatch(iss -> iss.getShortName().equals(ISSUE_TEXT1)));

        /* test reverse linkage*/
        assertEquals(project, issue.getProject());
    }


    @Test
    void addUsers() {
        // given - setup

        //when
        Set<User> boundUsers = project.addUsers(users);

        //then
        assertNotNull(project.getUsers());
        assertEquals(2, project.getUsers().size());
        assertTrue(project.getUsers().stream().anyMatch(user -> user.getGivenName().equals("Sergiu")));
        assertTrue(project.getUsers().stream().anyMatch(user -> user.getGivenName().equals("Mihai")));

        assertNotNull(boundUsers);
        Optional<User> user1 = boundUsers.stream().filter(user -> user.getGivenName().equals("Sergiu")).findFirst();
        Optional<User> user2 = boundUsers.stream().filter(user -> user.getGivenName().equals("Mihai")).findFirst();
        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        // check directly on objects
        assertTrue(user1.get().getProjects().stream().anyMatch(project1 -> project1 == project));
        assertTrue(user2.get().getProjects().stream().anyMatch(project2 -> project2 == project));
    }


    @Test
    void addUsersNull() {

        Assertions.assertThrows(RuntimeException.class, () -> project.addUsers(null));
    }

    @Test
    void addUsersEmpty() {

        Set<User> emptySet = new HashSet();

        Set<User> projectUsers  = project.addUsers(emptySet);

        Assertions.assertTrue(projectUsers.isEmpty());
    }


    @Test
    void addUser() {

        //when
        project.addUser(sergiu);

        //then
        assertNotNull(project.getUsers());
        assertEquals(1, project.getUsers().size());
        assertTrue(project.getUsers().stream().anyMatch(user1 -> user1.getGivenName().equals("Sergiu")));

        // reverse linkage
        assertTrue(sergiu.getProjects().stream().anyMatch(project1 -> project1 == project));
    }

    @Test
    void addDuplicateUser(){
        //when
        project.addUser(sergiu);
        project.addUser(sergiu);

        //then
        assertNotNull(project.getUsers());
        assertEquals(1, project.getUsers().size());
    }

    @Test
    void getWorkpackages(){

        // given
        Set<Workpackage> workpackages1 = new HashSet<>();
        workpackages1.add(Workpackage.builder().name("wp1").build());
        workpackages1.add(Workpackage.builder().name("wp2").build());

        Set<Workpackage> workpackages2 = new HashSet<>();
        workpackages2.add(Workpackage.builder().name("wp3").build());
        workpackages2.add(Workpackage.builder().name("wp4").build());

        Set<Issue> issues = new HashSet<>();
        issues.add(Issue.builder().shortName("is1").workpackages(workpackages1).build());
        issues.add(Issue.builder().shortName("is2").workpackages(workpackages2).build());

        project.addIssues(issues);
        // when
        Set<Workpackage> foundWorkpackages = project.getWorkpackages();

        // then
        assertNotNull(foundWorkpackages);
        assertEquals(4, foundWorkpackages.size());
    }

    @Test
    void getWorkpackagesWDuplicates(){
        /*If we have workpackages solving different issues, they should not appear duplicated in the list we return*/
        // given
        Set<Workpackage> workpackages = new HashSet<>();
        workpackages.add(Workpackage.builder().name("wp1").build());
        workpackages.add(Workpackage.builder().name("wp2").build());

        Set<Issue> issues = new HashSet<>();
        issues.add(Issue.builder().shortName("is1").workpackages(workpackages).build());
        workpackages.add(Workpackage.builder().name("wp3").build());

        issues.add(Issue.builder().shortName("is2").workpackages(workpackages).build());

        project.addIssues(issues);
        // when
        Set<Workpackage> foundWorkpackages = project.getWorkpackages();

        // then
        assertNotNull(foundWorkpackages);
        assertEquals(3, foundWorkpackages.size());
    }


    @Test
    void getActivities(){
        /*The object chain for retrieving activities associated to a Project is longer: Project-Issue-Workpackage-activities*/
        // given
        Set<Activity> activities1 = new HashSet<>();
        activities1.add(Activity.builder().description("ac1").build());
        activities1.add(Activity.builder().description("ac2").build());

        Set<Activity> activities2 = new HashSet<>();
        activities2.add(Activity.builder().description("ac3").build());
        activities2.add(Activity.builder().description("ac4").build());

        Set<Workpackage> workpackages = new HashSet<>();
        workpackages.add(Workpackage.builder().name("wp1").activities(activities1).build());
        workpackages.add(Workpackage.builder().name("wp2").activities(activities2).build());

        Set<Issue> issues = new HashSet<>();
        issues.add(Issue.builder().shortName("is1").workpackages(workpackages).build());

        project.addIssues(issues);
        // when
        Set<Activity> foundActivities = project.getActivities();

        // then
        assertNotNull(foundActivities);
        assertEquals(4, foundActivities.size());
    }

    @Test
    void getActivitiesWDuplicates(){
        /*If we have activities solving different workpackages, they should not appear duplicated in the list we return*/
        // given
        Set<Activity> activities = new HashSet<>();
        activities.add(Activity.builder().description("ac1").build());
        activities.add(Activity.builder().description("ac2").build());

        Set<Workpackage> workpackages = new HashSet<>();
        workpackages.add(Workpackage.builder().name("wp1").activities(activities).build());
        activities.add(Activity.builder().description("ac3").build());
        workpackages.add(Workpackage.builder().name("wp2").activities(activities).build());

        Set<Issue> issues = new HashSet<>();
        issues.add(Issue.builder().shortName("is1").workpackages(workpackages).build());

        project.addIssues(issues);

        // when
        Set<Activity> foundActivities = project.getActivities();

        // then
        assertNotNull(foundActivities);
        assertEquals(3, foundActivities.size());
    }
}