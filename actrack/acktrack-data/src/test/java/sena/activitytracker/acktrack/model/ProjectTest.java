package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        sergiu= User.builder().id(IDONE).givenName("Sergiu").build();
        mihai = User.builder().id(IDTWO).givenName("Mihai").build();
        users = new HashSet<>();
        users.add(sergiu);
        users.add(mihai);

        developer = Role.builder().id(IDONE).name("developer").build();
        roles = new HashSet<>();
        roles.add(developer);

        project = Project.builder()
                .id(IDONE)
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
        Assertions.assertThrows(RuntimeException.class, ()-> project.addIssues(emptySet));
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
    void addRoles() {
        // given
        final String ROLE_DESC1 = "first role";
        final String ROLE_DESC2 = "2nd role";
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().id(IDONE).description(ROLE_DESC1).build());
        roles.add(Role.builder().id(IDTWO).description(ROLE_DESC2).build());

        //when
        Set<Role> boundRoles = project.addRoles(roles);

        //then
        assertNotNull(project.getRoles());
        assertEquals(2, project.getRoles().size());
        assertTrue(project.getRoles().stream().anyMatch(role -> role.getDescription().equals(ROLE_DESC1)));
        assertTrue(project.getRoles().stream().anyMatch(role -> role.getDescription().equals(ROLE_DESC2)));

        assertNotNull(boundRoles);
        Optional<Role> role1 = boundRoles.stream().filter(role -> role.getDescription().equals(ROLE_DESC1)).findFirst();
        Optional<Role> role2 = boundRoles.stream().filter(role -> role.getDescription().equals(ROLE_DESC2)).findFirst();
        assertTrue(role1.isPresent());
        assertTrue(role2.isPresent());
        // check directly on objects
        assertTrue(role1.get().getProjects().stream().anyMatch(project1 -> project1 == project));
        assertTrue(role2.get().getProjects().stream().anyMatch(project2 -> project2 == project));

    }

    @Test
    void addRolesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> project.addRoles(null));
    }

    @Test
    void addRolesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> project.addRoles(emptySet));
    }

    @Test
    void addRole() {
        // given
        final String ROLE_DESC1 = "first role";
        Role role = Role.builder().id(IDONE).description(ROLE_DESC1).build();

        //when
        project.addRole(role);

        //then
        assertNotNull(project.getRoles());
        assertEquals(1, project.getRoles().size());
        assertTrue(project.getRoles().stream().anyMatch(role1 -> role1.getDescription().equals(ROLE_DESC1)));

        // reverse linkage
        assertTrue(role.getProjects().stream().anyMatch(project1 -> project1 == project));
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

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, () -> project.addUsers(emptySet));
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


    /*User-Role mapping tests- null tests, non-associates tests, happy-path tests*/

    @Test
    void testAddUserToRole(){

        // given - project, users, and roles
        project.addUsers(users);
        project.addRoles(roles);

        // when
        ProjectUserRole map1 = project.addUserToRole(sergiu, developer);
        ProjectUserRole map2 = project.addUserToRole(mihai,developer);

        assertNotNull(map1);
        assertNotNull(map2);

        assertEquals(2, project.getProjectUserRoles().size());
        assertEquals(2, project.getUsers().size());
        assertEquals(1, project.getRoles().size());

    }

    @Test
    void testAddUserToRoleUserNotAllocatedToProject(){
        // given - project, users, and roles
        /*Unknown users*/
        project.addRoles(roles);

        // when
        Assertions.assertThrows(RuntimeException.class, () -> project.addUserToRole(sergiu, developer));
    }

    @Test
    void testAddUserToRoleRoleNotAllocatedToProject(){
        // given - project, users, and roles
        /*Unknown users*/
        project.addUsers(users);

        // when
        Assertions.assertThrows(RuntimeException.class, () -> project.addUserToRole(sergiu, developer));
    }
    @Test
    void testAddUserToRoleUserIsNull(){
        // given - project, users, and roles
        project.addUsers(users);
        project.addRoles(roles);

        // when
        Assertions.assertThrows(RuntimeException.class, () -> project.addUserToRole(null, developer));
    }

    @Test
    void testAddUserToRoleRoleIsNull(){
        // given - project, users, and roles
        project.addUsers(users);
        project.addRoles(roles);

        // when
        Assertions.assertThrows(RuntimeException.class, () -> project.addUserToRole(sergiu, null));
    }
}