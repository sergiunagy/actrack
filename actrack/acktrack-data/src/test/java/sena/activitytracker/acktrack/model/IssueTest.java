package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IssueTest extends BaseDomTest {

    Issue issue;

    @BeforeEach
    void setUp() {
        issue = Issue.builder()
                .id(IDONE)
                .issue_id("14a8")
                .description("quality issue alpha")
                .link("url quality")
                .build();
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
        Set<Workpackage> boundWorkpackages = issue.addWorkpackages(workpackages);

        // then
        assertNotNull(issue.getWorkpackages());
        assertEquals(2, issue.getWorkpackages().size());
        assertTrue(issue.getWorkpackages().stream().anyMatch(user -> user.getDescription().equals(TXT1)));
        assertTrue(issue.getWorkpackages().stream().anyMatch(user -> user.getDescription().equals(TXT2)));

        assertNotNull(boundWorkpackages);
        Optional<Workpackage> workpackageOptional1 = boundWorkpackages.stream().filter(workpackage1 -> workpackage1.getDescription().equals(TXT1)).findFirst();
        Optional<Workpackage> workpackageOptional2 = boundWorkpackages.stream().filter(workpackage1 -> workpackage1.getDescription().equals(TXT2)).findFirst();
        assertTrue(workpackageOptional1.isPresent());
        assertTrue(workpackageOptional2.isPresent());
        // check directly on objects
        assertTrue(workpackageOptional1.get().getIssues().stream().anyMatch(issue1 -> issue1 == issue));
        assertTrue(workpackageOptional2.get().getIssues().stream().anyMatch(issue1 -> issue1 == issue));

    }

    @Test
    void addWorkpackagesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> issue.addWorkpackages(null));
    }

    @Test
    void addWorkpackagesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> issue.addWorkpackages(emptySet));
    }

    @Test
    void addWorkpackage() {
        // given
        final String TXT1 = "first";
        Workpackage workpackage = Workpackage.builder().id(IDONE).description(TXT1).build();

        // when
        issue.addWorkpackage(workpackage);

        // then
        assertNotNull(issue.getWorkpackages());
        assertEquals(1, issue.getWorkpackages().size());
        assertTrue(issue.getWorkpackages().stream().anyMatch(workpackage1 -> workpackage1.getDescription().equals(TXT1)));

        // check directly on objects
        assertTrue(workpackage.getIssues().stream().anyMatch(issue1 -> issue1 == issue));
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
        Set<Activity> boundWorkpackages = issue.addActivities(activities);

        // then
        assertNotNull(issue.getActivities());
        assertEquals(2, issue.getActivities().size());
        assertTrue(issue.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT1)));
        assertTrue(issue.getActivities().stream().anyMatch(activity -> activity.getDescription().equals(TXT2)));

        assertNotNull(boundWorkpackages);
        Optional<Activity> activityOptional1 = boundWorkpackages.stream().filter(activity -> activity.getDescription().equals(TXT1)).findFirst();
        Optional<Activity> activityOptional2 = boundWorkpackages.stream().filter(activity -> activity.getDescription().equals(TXT2)).findFirst();
        assertTrue(activityOptional1.isPresent());
        assertTrue(activityOptional2.isPresent());

        // check directly on objects
        assertTrue(activityOptional1.get().getIssues().stream().anyMatch(issue1 -> issue1 == issue));
        assertTrue(activityOptional2.get().getIssues().stream().anyMatch(issue1 -> issue1 == issue));
    }

    @Test
    void addActivitiesNull() {

        Assertions.assertThrows(RuntimeException.class, ()-> issue.addActivities(null));
    }

    @Test
    void addActivitiesEmpty() {

        Set emptySet = new HashSet();
        Assertions.assertThrows(RuntimeException.class, ()-> issue.addActivities(emptySet));
    }
}