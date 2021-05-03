package sena.activitytracker.acktrack.controllers.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.repositories.security.UserRepository;
import sena.activitytracker.acktrack.services.ActivityService;
import sena.activitytracker.acktrack.services.IssueService;
import sena.activitytracker.acktrack.services.ProjectService;
import sena.activitytracker.acktrack.services.WorkpackageService;
import sena.activitytracker.acktrack.services.security.RoleService;
import sena.activitytracker.acktrack.services.security.UserService;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest  /* Use WebMvcTest to load a lightweight spring MVC only context for testing*/
class ActivitiesControllerV2TestIT {

    private static final String BOOKINGS_CALENDAR_PAGE = "/activities/bookings_calendar";
    private static final String CALENDAR_PAGE_LINK = "/api/v1/calendar";


    @Autowired
    WebApplicationContext wac;

    /* Mock the Spring Context Controller dependencies*/
    @MockBean
    ActivityService activityService;
    @MockBean
    IssueService issueService;
    @MockBean
    ProjectService projectService;
    @MockBean
    WorkpackageService workpackageService;
    @MockBean
    UserService userService;

    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;


    @BeforeEach
    void setUp() {

        /* Load Spring MVC context*/
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();

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

        mockMvc.perform(get(CALENDAR_PAGE_LINK).with(httpBasic("user2", "user")))
                .andExpect(status().isOk())
                .andExpect(view().name(BOOKINGS_CALENDAR_PAGE));
    }

    @Test
    void showBookingsCalendarWithHttpBasicNeg() throws Exception {

        mockMvc.perform(get(CALENDAR_PAGE_LINK).with(httpBasic("wronguser", "user")))
                .andExpect(status().isUnauthorized());
    }

}