package sena.activitytracker.acktrack.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sena.activitytracker.acktrack.dtos.WorkpackageDTO;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.services.WorkpackageService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WorkpackageControllerTest {

    private static final String LIST_WORKPACKAGES_PAGE = "/workpackages/workpackages_list";

    @Mock
    WorkpackageService workpackageService;

    @InjectMocks
    WorkpackageController workpackageController;

    Set<WorkpackageDTO> workpackageSet;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        workpackageSet = new HashSet<>();
        workpackageSet.add(WorkpackageDTO.builder()
                .startDate(LocalDate.now())
                .build());
        workpackageSet.add(WorkpackageDTO.builder()
                .startDate(LocalDate.now().minusDays(1))
                .build());

        mockMvc = MockMvcBuilders.standaloneSetup(workpackageController).build();
    }

    @Test
    void listWorkpackages() throws Exception {

        when(workpackageService.findAll()).thenReturn(workpackageSet);

        mockMvc.perform(get("/list_workpackages"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_WORKPACKAGES_PAGE));
//                .andExpect(model().attribute("issues", hasSize(2)));
    }
}