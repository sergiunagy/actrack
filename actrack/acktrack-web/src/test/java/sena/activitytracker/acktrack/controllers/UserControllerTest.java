package sena.activitytracker.acktrack.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.services.security.UserService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final String LIST_USERS_PAGE = "/users/users_list";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    MockMvc mockMvc;
    Set<User> userSet;

    @BeforeEach
    void setUp() {
        /* Create a couple of dummy users before each test*/
        userSet = new HashSet<>();

        userSet.add(User.builder().username("test1").build());
        userSet.add(User.builder().username("test2").build());

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void listUsers() throws Exception {

        /* given - users list*/
        /* when - service triggers */
        when(userService.findAll()).thenReturn(userSet);

        /*execute*/
        mockMvc.perform(get("/list_teammates"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_USERS_PAGE))
                .andExpect(model().attribute("users", hasSize(2)));
    }
}