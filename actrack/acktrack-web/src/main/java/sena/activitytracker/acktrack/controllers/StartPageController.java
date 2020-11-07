package sena.activitytracker.acktrack.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class StartPageController {

    @GetMapping({"/","/index", "index.html"})
    public String getIndexPage(){

        return "index";
    }
}
