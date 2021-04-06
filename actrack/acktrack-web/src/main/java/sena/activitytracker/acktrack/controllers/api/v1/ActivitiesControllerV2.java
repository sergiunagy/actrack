package sena.activitytracker.acktrack.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ActivitiesControllerV2 {

    private static final String BOOKINGS_CALENDAR_PAGE = "/activities/bookings_calendar";

    @GetMapping("/calendar")
    public String showBookingsCalendar(Model model){

        return BOOKINGS_CALENDAR_PAGE;
    }
}
