package sena.activitytracker.acktrack.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.services.ActivityService;
import sena.activitytracker.acktrack.services.ActivityServiceImpl;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Controller
public class AdminStartupController {

    @GetMapping({"/administrative", "/admin", "/administrative/", "/admin/", "/administrative.html"})
    public String loadAdminStartup(Model model){

        /*TODO: tempoarily hardwired to an Overview page in the idea of allowing the configuration of the first page for a role
           - maybe this extra flexbility doesn't make much sense and we could simply load the page ?*/
        return "redirect:/overview/";
    }

}
