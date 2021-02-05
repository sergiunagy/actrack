package sena.activitytracker.acktrack.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.services.ProjectService;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Controller
public class ProjectController {

    public static final String LIST_PROJECTS_PAGE = "/projects/projects_list";

    private final ProjectService projectService;

    @GetMapping("/list_projects")
    public String listProjects(Model model){

        Set<ProjectDTO> projects = projectService.findAll();

        model.addAttribute("projects",
                projects.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList())
        );

        return LIST_PROJECTS_PAGE;
    }

}
