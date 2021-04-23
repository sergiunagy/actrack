package sena.activitytracker.acktrack.integration.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sena.activitytracker.acktrack.controllers.ActivityController;
import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.services.ActivityService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class ActivityControllerTestIT {

    @Autowired
    WebApplicationContext wac;

    private static final String LIST_ACTIVITIES_PAGE = "/activities/activities_list";
    private static final String BOOKINGS_CALENDAR_PAGE = "/activities/bookings_calendar";


    Set<ActivityDTO> activitySet;

    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        /*Initialize with a mock Activities list*/
        activitySet = new HashSet<>();
        activitySet.add(ActivityDTO.builder().createdTimestamp(Timestamp.valueOf(LocalDateTime.now())).date(LocalDate.now().minusDays(1)).build());
        activitySet.add(ActivityDTO.builder().createdTimestamp(Timestamp.valueOf(LocalDateTime.now())).date(LocalDate.now()).build());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void createNewBookingTest() throws Exception{

        mockMvc.perform(get("/calendar/create_booking")
                .with(httpBasic("guru", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_ACTIVITIES_PAGE));
    }

}
