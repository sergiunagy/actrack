package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.ActivityRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    ActivityRepository activityRepository;

    @InjectMocks
    ActivityServiceImpl activityService;

    Activity review, quality;
    Set<Activity> activitySet =new HashSet<>();

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        quality = Activity.builder()
                .id(1L)
                .description("Check quality issues")
                .duration(Duration.of(4, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 18))
                .isExported(false)
                .build();

        review = Activity.builder()
                .id(1L)
                .description("Execute review on beta")
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 19))
                .isExported(false)
                .build();


        activitySet.add(review);
        activitySet.add(quality);
    }

    @Test
    void findAll() {

        when(activityRepository.findAll()).thenReturn(activitySet);

        Set<Activity> foundActivities = activityService.findAll();

        assertNotNull(foundActivities);
        assertEquals(2, foundActivities.size());
        verify(activityRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(review));

        Activity foundActivity = activityService.findById(review.getId());

        assertNotNull(foundActivity);
        assertEquals(1, foundActivity.getId());
        verify(activityRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {

        when(activityRepository.save(any(Activity.class))).thenReturn(review);

        Activity foundActivity = activityService.save(review);

        assertNotNull(foundActivity);
        assertEquals(1, foundActivity.getId());
        verify(activityRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(activityRepository.saveAll(any(Set.class))).thenReturn(activitySet);

        Set<Activity> foundActivitys = activityService.saveAll(activitySet);

        assertNotNull(foundActivitys);
        assertEquals(2, foundActivitys.size());
        verify(activityRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        activityService.delete(review);

        verify(activityRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        activityService.deleteById(1L);

        verify(activityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetActivitiesBetweenDates() {

        when(activityRepository.findAllByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(activitySet);

        Set<Activity> foundActivities = activityRepository.findAllByDateBetween(LocalDate.now().minusDays(1), LocalDate.now());

        assertNotNull(foundActivities);
        assertEquals(2, foundActivities.size());
        verify(activityRepository, times(1)).findAllByDateBetween(any(LocalDate.class), any(LocalDate.class));

    }
}