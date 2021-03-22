package sena.activitytracker.acktrack.controllers.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ActivitiesControllerV2 {

    private static final String BOOKINGS_CALENDAR_PAGE = "/activities/bookings_calendar";

    @GetMapping("/api/calendar")
    public String showBookingsCalendar(Model model){

        return BOOKINGS_CALENDAR_PAGE;
    }
}
