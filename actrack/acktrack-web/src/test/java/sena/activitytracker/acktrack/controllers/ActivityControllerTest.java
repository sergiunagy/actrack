package sena.activitytracker.acktrack.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sena.activitytracker.acktrack.services.ActivityService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {

    private static final String LIST_ACTIVITIES_PAGE = "/overview/administrative_overview";

    @Mock
    ActivityService activityService;

    @InjectMocks
    ActivityController activityController;
    MockMvc mockMvc;


    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(activityController)
                .build();
    }

    @Test
    void listActivities() throws Exception{

        mockMvc.perform(get("/list_activities"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_ACTIVITIES_PAGE));
    }
}