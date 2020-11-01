package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sena.activitytracker.acktrack.repositories.ProjectRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    ProjectRepository projectRepository;

    Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id(1L)
                .name("alpha")
                .description("dummy alpha")
                .mainLocation("Alpha location")
                .plannedEndDate("21.10.2020")
                .actualEndDate("28.10.2020")
                .plannedSopDate("28.10.2021")
                .plannedEndDate("28.10.2022")
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
        issues.add(Issue.builder().id(1L).shortName(ISSUE_TEXT1).build());
        issues.add(Issue.builder().id(2L).shortName(ISSUE_TEXT2).build());

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
    void addIssue() {
        // given
        final String ISSUE_TEXT1 = "first issue";
        Issue issue = Issue.builder().id(1L).shortName(ISSUE_TEXT1).build();

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
        roles.add(Role.builder().id(1L).description(ROLE_DESC1).build());
        roles.add(Role.builder().id(2L).description(ROLE_DESC2).build());

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
    void addRole() {
        // given
        final String ROLE_DESC1 = "first role";
        Role role = Role.builder().id(1L).description(ROLE_DESC1).build();

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
        // given
        final String TXT1 = "first";
        final String TXT2 = "2nd ";
        Set<User> users = new HashSet<>();
        users.add(User.builder().id(1L).givenName(TXT1).build());
        users.add(User.builder().id(2L).givenName(TXT2).build());

        //when
        Set<User> boundUsers = project.addUsers(users);

        //then
        assertNotNull(project.getUsers());
        assertEquals(2, project.getUsers().size());
        assertTrue(project.getUsers().stream().anyMatch(user -> user.getGivenName().equals(TXT1)));
        assertTrue(project.getUsers().stream().anyMatch(user -> user.getGivenName().equals(TXT2)));

        assertNotNull(boundUsers);
        Optional<User> user1 = boundUsers.stream().filter(user -> user.getGivenName().equals(TXT1)).findFirst();
        Optional<User> user2 = boundUsers.stream().filter(user -> user.getGivenName().equals(TXT2)).findFirst();
        assertTrue(user1.isPresent());
        assertTrue(user2.isPresent());
        // check directly on objects
        assertTrue(user1.get().getProjects().stream().anyMatch(project1 -> project1 == project));
        assertTrue(user2.get().getProjects().stream().anyMatch(project2 -> project2 == project));
    }

    @Test
    void addUser() {
        final String TXT1 = "first";
        // given
        final String ROLE_DESC1 = "first role";
        User user = User.builder().id(1L).givenName(TXT1).build();

        //when
        project.addUser(user);

        //then
        assertNotNull(project.getUsers());
        assertEquals(1, project.getUsers().size());
        assertTrue(project.getUsers().stream().anyMatch(user1 -> user1.getGivenName().equals(TXT1)));

        // reverse linkage
        assertTrue(user.getProjects().stream().anyMatch(project1 -> project1 == project));
    }

    @Test
    void addProjectUserRolesSet() {
        // given
        // composite keys for the project-user-role mapping
        UserRoleKey key1 = new UserRoleKey(1L,1L);
        UserRoleKey key2 = new UserRoleKey(1L,2L);

        Set<ProjectUserRoles> projectUserRoles = new HashSet<>();
        projectUserRoles.add(ProjectUserRoles.builder().userRoleKey(key1).build());
        projectUserRoles.add(ProjectUserRoles.builder().userRoleKey(key2).build());

        //when
        Set<ProjectUserRoles> boundProjectUserRoles = project.addProjectUserRolesSet(projectUserRoles);

        //then
        assertNotNull(project.getProjectUserRoles());
        assertEquals(2, project.getProjectUserRoles().size());
        assertTrue(project.getProjectUserRoles().stream().anyMatch(projectUserRoles1 -> projectUserRoles1.getUserRoleKey()==key1));
        assertTrue(project.getProjectUserRoles().stream().anyMatch(projectUserRoles1 -> projectUserRoles1.getUserRoleKey()==key2));

        assertNotNull(boundProjectUserRoles);
        Optional<ProjectUserRoles> projectUserRolesStream1 = boundProjectUserRoles.stream().filter(projectUserRoles1 -> projectUserRoles1.getUserRoleKey()==key1).findFirst();
        Optional<ProjectUserRoles> projectUserRolesStream2 = boundProjectUserRoles.stream().filter(projectUserRoles1 -> projectUserRoles1.getUserRoleKey()==key2).findFirst();
        assertTrue(projectUserRolesStream1.isPresent());
        assertTrue(projectUserRolesStream2.isPresent());
        // check directly on objects
        assertEquals(project, projectUserRolesStream1.get().getProject());
        assertEquals(project, projectUserRolesStream2.get().getProject());
    }

    @Test
    void addProjectUserRoles() {
        UserRoleKey key1 = new UserRoleKey(1L,1L);

        ProjectUserRoles projectUserRoles = ProjectUserRoles.builder().userRoleKey(key1).build();

        //when
        project.addProjectUserRoles(projectUserRoles);

        //then
        assertNotNull(project.getProjectUserRoles());
        assertEquals(1, project.getProjectUserRoles().size());
        assertTrue(project.getProjectUserRoles().stream().anyMatch(projectUserRoles1 -> projectUserRoles1.getUserRoleKey()==key1));

        // check directly on objects
        assertEquals(project, projectUserRoles.getProject());
    }
}