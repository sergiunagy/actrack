package sena.activitytracker.acktrack.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.services.ProjectService;

import java.util.Set;

@Slf4j
@AllArgsConstructor
@Controller
public class ProjectController {

    public static final String LIST_PROJECTS_PAGE = "/projects/projects_list";

    private final ProjectService projectService;

    @GetMapping("/list_projects")
    public String listProjects(Model model){

        /*TODO: replace with DTO*/
        Set<Project> projects = projectService.findAll();

        model.addAttribute("projects", projects); /*todo: sort by creation date*/

        return LIST_PROJECTS_PAGE;
    }

}
