package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest extends BaseDomTest {

    Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(IDONE)
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();
    }

    @Test
    void addProjects() {
        // given
        final String TXT1 = "first";
        final String TXT2 = "2nd ";
        Set<Project> projects = new HashSet<>();
        projects.add(Project.builder().id(IDONE).description(TXT1).build());
        projects.add(Project.builder().id(IDTWO).description(TXT2).build());

        // when
        Set<Project> boundProjects = role.addProjects(projects);

        // then
        assertNotNull(role.getProjects());
        assertEquals(2, role.getProjects().size());
        assertTrue(role.getProjects().stream().anyMatch(user -> user.getDescription().equals(TXT1)));
        assertTrue(role.getProjects().stream().anyMatch(user -> user.getDescription().equals(TXT2)));

        assertNotNull(boundProjects);
        Optional<Project> projectOptional1 = boundProjects.stream().filter(project1 -> project1.getDescription().equals(TXT1)).findFirst();
        Optional<Project> projectOptional2 = boundProjects.stream().filter(project1 -> project1.getDescription().equals(TXT2)).findFirst();
        assertTrue(projectOptional1.isPresent());
        assertTrue(projectOptional2.isPresent());
        // check directly on objects
        assertTrue(projectOptional1.get().getRoles().stream().anyMatch(role1 -> role1 == role));
        assertTrue(projectOptional2.get().getRoles().stream().anyMatch(role1 -> role1 == role));

    }

    @Test
    void addProjectsNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> role.addProjects(null));
    }

    @Test
    void addProjectsEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> role.addProjects(emptySet));
    }

    @Test
    void addProject() {
        // given
        final String TXT1 = "first";
        Project project = Project.builder().id(IDONE).description(TXT1).build();

        // when
        role.addProject(project);

        // then
        assertNotNull(role.getProjects());
        assertEquals(1, role.getProjects().size());
        assertTrue(role.getProjects().stream().anyMatch(user -> user.getDescription().equals(TXT1)));

        // check directly on objects
        assertTrue(project.getRoles().stream().anyMatch(role1 -> role1 == role));

    }

    @Test
    void addProjectNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> role.addProject(null));
    }

}