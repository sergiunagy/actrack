package sena.activitytracker.acktrack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrativeController {

    private static final String ADMINISTRATIVE_INDEX_PAGE = "/administration/index";

    @GetMapping({"/administrative", "/admin", "/administrative/", "/admin/", "/administrative.html"})
    public String getIndex(){

        return ADMINISTRATIVE_INDEX_PAGE;
    }
}
