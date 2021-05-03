package sena.activitytracker.acktrack.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sena.activitytracker.acktrack.dtos.ActivityDTO;
import sena.activitytracker.acktrack.services.ActivityService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ActivitiesControllerV2 {

    private static ActivityService activityService;

    private static final String BOOKINGS_CALENDAR_PAGE = "/activities/bookings_calendar";

    @GetMapping("/calendar")
    public String showBookingsCalendar(Model model){

        return BOOKINGS_CALENDAR_PAGE;
    }

    public String createNewBooking(){

        return "Dummy";
    }


    @PutMapping(path = {"/calendar/{activityId}"}, produces = {"application/json"})
    public ResponseEntity updateActivityBooking(@PathVariable("activityId") Long activityId,
                                                @Validated @RequestBody ActivityDTO activityDTO){

        /*todo: */
        //activityService.updateActivity(activityId, activityDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/calendar/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActivityBooking(@PathVariable("activityId") Long activityId){
        activityService.deleteById(activityId);
    }
}
