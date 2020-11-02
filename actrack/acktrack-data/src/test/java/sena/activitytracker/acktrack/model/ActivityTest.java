package sena.activitytracker.acktrack.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sena.activitytracker.acktrack.repositories.ActivityRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


class ActivityTest {

    Activity activity;

    @BeforeEach
    void setUp() {
        activity = Activity.builder()
                .id(1L)
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