package sena.activitytracker.acktrack.controllers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.services.IssueService;

import java.util.Set;

@Slf4j
@Controller
@AllArgsConstructor
public class IssueController {
    public static final String LIST_ISSUES_PAGE = "/issues/issues_list";

    private final IssueService issueService;

    @GetMapping("/list_issues")
    public String listIssues(Model model){

        Set<Issue> issueSet = issueService.findAll();
        /*TODO : to DTO list ordered by creation date*/
        model.addAttribute("issues", issueSet);

        return LIST_ISSUES_PAGE;
    }
}
