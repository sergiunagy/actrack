package sena.activitytracker.acktrack.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sena.activitytracker.acktrack.dtos.WorkpackageDTO;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.services.WorkpackageService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@AllArgsConstructor
public class WorkpackageController {

    private final String LIST_WORKPACKAGES_PAGE = "/workpackages/workpackages_list";
    private final WorkpackageService workpackageService;

    @GetMapping("/list_workpackages")
    public String listWorkpackages(Model model){

        Set<WorkpackageDTO> workpackages = workpackageService.findAll();

        model.addAttribute("workpackages",
                workpackages.stream().collect(Collectors.toList())); /*todo: sort*/
        return LIST_WORKPACKAGES_PAGE;
    }
}
