package sena.activitytracker.acktrack.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sena.activitytracker.acktrack.dtos.UserDTO;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.services.security.UserService;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private static final String LIST_USERS_PAGE = "/users/users_list";

    private final UserService userService;

    @GetMapping("/list_teammates")
    public String listUsers(Model model){

        Set<UserDTO> users = userService.findAll();

        model.addAttribute("users", users);
        return LIST_USERS_PAGE;
    }
}
