package sena.activitytracker.acktrack.integration.controllers.api.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sena.activitytracker.acktrack.controllers.api.v1.ActivitiesControllerV2;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.services.ActivityService;

import java.time.Duration;
import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class ActivitiesControllerV2TestIT {

    private static final String BOOKINGS_CALENDAR_PAGE = "/activities/bookings_calendar";
    private static final String CALENDAR_PAGE_LINK = "/api/v1/calendar/";

    @Autowired
    public ActivityService activityService;

    public ActivitiesControllerV2 activitiesControllerV2;
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    public void setup(){

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testDeleteActivityBooking() throws Exception{

        /* prepare */
        Activity newActivity = Activity.builder()
                .duration(Duration.ofHours(8))
                .date(LocalDate.now())
                .isExported(false)
                .description("delete test")
                .build();

        Activity saved = activityService.save(newActivity);

        /* run test - this will trigger a successful authentication*/
        mockMvc.perform(delete(CALENDAR_PAGE_LINK + saved.getId())
                    .header("Api-Key", "guru")
                    .header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }


    @Test
    void testDeleteActivityBookingBadCredentials() throws Exception{

        /* prepare */
        Activity newActivity = Activity.builder()
                .duration(Duration.ofHours(8))
                .date(LocalDate.now())
                .isExported(false)
                .description("delete test")
                .build();

        Activity saved = activityService.save(newActivity);

        /* run test - this will trigger a successful authentication*/
        mockMvc.perform(delete(CALENDAR_PAGE_LINK + saved.getId())
                .header("Api-Key", "guru")
                .header("Api-Secret", "guru_bad"))
                .andExpect(status().isUnauthorized());
    }

    /* Instruct Spring Test context we are authenticated with user. This does NOT trigger auth */
    @WithMockUser("whateveruser")
    @Test
    void showBookingsCalendar() throws Exception {

        mockMvc.perform(get(CALENDAR_PAGE_LINK))
                .andExpect(status().isOk())
                .andExpect(view().name(BOOKINGS_CALENDAR_PAGE));
    }

    @Test
    void showBookingsCalendarWithHttpBasic() throws Exception {

        /* this should be passed through our custom REST filter and Forwarded to the http */
        mockMvc.perform(get(CALENDAR_PAGE_LINK).with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andExpect(view().name(BOOKINGS_CALENDAR_PAGE));
    }

    @Test
    void showBookingsCalendarWithHttpBasicNeg() throws Exception {

        mockMvc.perform(get(CALENDAR_PAGE_LINK).with(httpBasic("wronguser", "user")))
                .andExpect(status().isUnauthorized());
    }

}
