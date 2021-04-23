package sena.activitytracker.acktrack.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.services.ActivityService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ActivityControllerTest {

    private static final String LIST_ACTIVITIES_PAGE = "/activities/activities_list";
    private static final String BOOKINGS_CALENDAR_PAGE = "/activities/bookings_calendar";


    Set<ActivityDTO> activitySet;

    @Mock
    ActivityService activityService;

    @InjectMocks
    ActivityController activityController;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        /*Initialize with a mock Activities list*/
        activitySet = new HashSet<>();
        activitySet.add(ActivityDTO.builder().createdTimestamp(Timestamp.valueOf(LocalDateTime.now())).date(LocalDate.now().minusDays(1)).build());
        activitySet.add(ActivityDTO.builder().createdTimestamp(Timestamp.valueOf(LocalDateTime.now())).date(LocalDate.now()).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(activityController)
                .build();
    }

    @Test
    void listActivitiesTest() throws Exception{

        // given setup

        // when
        when(activityService.listAllActivities()).thenReturn(activitySet);

        mockMvc.perform(get("/list_activities"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_ACTIVITIES_PAGE))
                .andExpect(model().attribute("activities", hasSize(2)));
    }

    @Test
    void showBookingsCalendarTest() throws Exception{

        /* request activities for current user and date range :
        * - requires user id - from authentication
        * - calculate month based on current date
         */

        User user = User.builder().id(1).build();

        /* pass uid and date range as request parameters */
        mockMvc.perform(get("/get_bookings_calendar"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_ACTIVITIES_PAGE))
                .andExpect(model().attribute("activities", hasSize(2)));
    }
}