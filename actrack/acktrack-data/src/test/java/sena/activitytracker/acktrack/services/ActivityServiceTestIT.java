package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTestIT {

    @Mock
    ActivityRepository activityRepository;

    @InjectMocks
    ActivityServiceImpl activityService;

    @BeforeEach
    void setUp() {
    }


    @Test
    void getActivitiesInDateRange() {


        int MAXRANGE = 10; //days

        Set<Activity> activities = new HashSet<>();

        IntStream.range(0, MAXRANGE).parallel().forEach(
                idx -> {
                    activities.add(Activity.builder()
                            .id(Long.valueOf(idx))
                            .date(LocalDate.now().minusDays((MAXRANGE - idx)))
                            .description("activity" + idx)
                            .build());
                }
        );

        when(activityRepository.saveAll(anySet())).thenReturn(activities);

        Set<Activity> savedActivities = activityService.saveAll(activities);
        if(savedActivities == null) System.out.println("null saved activities returned");

        Set<Activity> databaseActivities = activityService.findAll();
        System.out.println(activities);
        System.out.println(savedActivities);
    }
}