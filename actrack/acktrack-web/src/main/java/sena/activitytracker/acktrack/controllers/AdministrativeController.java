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
public class AdministrativeController {

    private static final String ADMINISTRATIVE_INDEX_PAGE = "/administration/index";

    private final ActivityService activityService;


    @GetMapping({"/administrative", "/admin", "/administrative/", "/admin/", "/administrative.html"})
    public String loadIndexPage(Model model){

        /*load page intended as index page for this role*/
        return getOverview(model);
    }

    /*Returns an overview page for this Role*/
    private String getOverview(Model model){

        /*Phase 1: return an activities report for all users - no DTOs yet
        * todo: in the future we'll implement and intermediate DTO layer to abstract the data module*/
        Set<Activity> activities = activityService.findAll();
        /*pass the activities ordered by date:*/
        model.addAttribute("activities",
                activities.stream()
                .sorted()
                .collect(Collectors.toList()));

    return ADMINISTRATIVE_INDEX_PAGE;
    }

}
