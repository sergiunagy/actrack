package sena.activitytracker.acktrack.integration.controllers.api.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sena.activitytracker.acktrack.controllers.api.v1.ActivitiesControllerV2;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.services.ActivityService;

import java.time.Duration;
import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
public class ActivitiesControllerV2TestIT {

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

        /* run test */
        mockMvc.perform(delete("/calendar/" + saved.getId())
                    .header("Api-Key", "guru")
                    .header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }
}
