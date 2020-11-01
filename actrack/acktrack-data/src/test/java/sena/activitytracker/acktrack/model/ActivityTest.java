package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    Activity activity;

    @BeforeEach
    void setUp() {
        activity = Activity.builder()
                .id(1L)
                .description("Check quality issues")
                .startDateTime("30.10.2020")
                .isExported(false)
                .build();
    }

    @Test
    void addUser() {
        final String TXT1 = "first";
        // given
        final String ROLE_DESC1 = "first role";
        User user = User.builder().id(1L).givenName(TXT1).build();

        //when
        activity.addUser(user);

        //then
        assertNotNull(activity.getUser());
        assertTrue(activity.getUser().getGivenName().equals(TXT1));

        // reverse linkage
        assertTrue(user.getActivities().stream().anyMatch(activity1 -> activity1 == activity));
    }


    @Test
    void addUserNull() {

        Assertions.assertThrows(RuntimeException.class, () -> activity.addUser(null));
    }
}