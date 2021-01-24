package sena.activitytracker.acktrack.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class OverviewController {

    /*TODO : temporarily hardwired to redirect to Activity list since we have no overview at the moment*/

    @GetMapping("/overview")
    public String loadOverview(Model model){

        return "redirect:/list_activities/";
    }
}
