package sena.activitytracker.acktrack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeveloperController {

    @GetMapping({"/developer", "/dev", "/developer/", "/dev/", "/developer.html"})
    public String getIndex(){

        return "developer/developer_overview";
    }
}
