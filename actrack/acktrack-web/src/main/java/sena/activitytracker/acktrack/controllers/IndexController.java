package sena.activitytracker.acktrack.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"/","/index", "index.html"})
    public String getIndexPage(){
        /*TODO : user role identification and predirect to proper landing page. Here: hardwired to admin*/
        return "redirect:/get_bookings_calendar";
    }
}