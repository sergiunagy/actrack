package sena.activitytracker.acktrack.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.services.ActivityService;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ActivityController {
    private static final String LIST_ACTIVITIES_PAGE = "/activities/activities_list";

    private final ActivityService activityService;

    /*Returns an overview page for this Role*/
    @GetMapping("/list_activities")
    private String listActivities(Model model){

        /*Phase 1: return an activities report for all users - no DTOs yet
         * todo: in the future we'll implement and intermediate DTO layer to abstract the data module*/
        Set<ActivityDTO> activities = activityService.listAllActivities();
        /*pass the activities ordered by date:*/

        model.addAttribute("activities",
                activities.stream()
                        .sorted((a1, a2)-> a2.getCreatedTimestamp().compareTo(a1.getCreatedTimestamp()))
                        .collect(Collectors.toList()));

        return LIST_ACTIVITIES_PAGE;
    }
}
