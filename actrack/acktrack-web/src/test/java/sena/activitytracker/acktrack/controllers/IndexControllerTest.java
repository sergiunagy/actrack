package sena.activitytracker.acktrack.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sena.activitytracker.acktrack.services.ActivityService;
import sena.activitytracker.acktrack.services.IssueService;
import sena.activitytracker.acktrack.services.ProjectService;
import sena.activitytracker.acktrack.services.WorkpackageService;
import sena.activitytracker.acktrack.services.security.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class IndexControllerTest {

    private static final String INDEX_PAGE = "/";

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

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getIndexPageNoSec() throws Exception {
        /* unrestricted access to root ? */
        mockMvc.perform(get(INDEX_PAGE))
                .andExpect(status().is3xxRedirection());
    }
}