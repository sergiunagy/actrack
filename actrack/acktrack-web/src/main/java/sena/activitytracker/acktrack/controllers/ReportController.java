package sena.activitytracker.acktrack.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ReportController {

    public static final String LIST_REPORTS_PAGE = "/reports/reports_list";

    @GetMapping("/list_reports")
    public String listReports(Model model){

        return LIST_REPORTS_PAGE;
    }
}
