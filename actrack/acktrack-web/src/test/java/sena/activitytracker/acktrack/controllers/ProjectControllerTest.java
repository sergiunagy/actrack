package sena.activitytracker.acktrack.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.services.ProjectService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {
    /*TODO : switch to DTOs after implementation*/
    public static final String LIST_PROJECTS_PAGE = "/projects/projects_list";

    Set<Project> projectSet;

    @Mock
    ProjectService projectService;

    @InjectMocks
    ProjectController projectController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        /*Set of mock projects*/
        projectSet = new HashSet<>();
        projectSet.add(Project.builder().actualStartDate(LocalDate.now()).build());
        projectSet.add(Project.builder().actualStartDate(LocalDate.now()).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(projectController)
                .build();
    }

    @Test
    void listProjects() throws Exception {
        // given setup

        // when
        when(projectService.findAll()).thenReturn(projectSet);

        mockMvc.perform(get("/list_projects"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_PROJECTS_PAGE))
                .andExpect(model().attribute("projects", hasSize(2)));
    }
}