package sena.activitytracker.acktrack.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.services.IssueService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IssueControllerTest {
    public static final String LIST_ISSUES_PAGE = "/issues/issues_list";


    Set<Issue> issueSet = new HashSet<>();


    @Mock
    IssueService issueService;

    @InjectMocks
    IssueController issueController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        /* Create a list of Issues to be returned with the mock service */
        /*Todo change from Issue to IssueDTO*/
        issueSet.add(Issue.builder().issue_id("Issue1").build());
        issueSet.add(Issue.builder().issue_id("Issue2").build());

        mockMvc = MockMvcBuilders.standaloneSetup(issueController).build();
    }

    @Test
    public void listIssuesTest() throws Exception{

        when(issueService.findAll()).thenReturn(issueSet);

        mockMvc.perform(get("/list_issues"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_ISSUES_PAGE))
                .andExpect(model().attribute("issues", hasSize(2)));
    }
}