package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sena.activitytracker.acktrack.model.security.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest extends BaseDomTest {

    Activity activity;

    @BeforeEach
    void setUp() {
        activity = Activity.builder()
                .id(IDONE)
                .description("Check quality issues")
                .date(LocalDate.of(2020, 10, 20))
                .isExported(false)
                .build();
    }

    @Test
    void addUser() {
        final String TXT1 = "first";
        // given
        final String ROLE_DESC1 = "first role";
        User user = User.builder().givenName(TXT1).build();

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