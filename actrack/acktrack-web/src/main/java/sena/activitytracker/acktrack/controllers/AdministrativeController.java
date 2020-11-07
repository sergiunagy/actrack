package sena.activitytracker.acktrack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrativeController {

    @GetMapping({"/administrative", "/admin", "/administrative/", "/admin/", "/administrative.html"})
    public String getIndex(){

        return "administration/administrative_overview";
    }
}
