package org.planpal.controller;

import org.planpal.dto.DailyEventDTO;
import org.planpal.service.DailyEventService;
import org.planpal.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    private final DailyEventService dailyEventService;
    private final ReviewService reviewService;

    @Autowired
    public HomeController(DailyEventService dailyEventService, ReviewService reviewService) {
        this.dailyEventService = dailyEventService;
        this.reviewService = reviewService;
    }

    @GetMapping({"", "/"})
    public String home(Model model) {

        int groupId = reviewService.getTopGroupIds();
        ArrayList<DailyEventDTO> dailyEvents = dailyEventService.getDailyEventsByGroupId(groupId);

        model.addAttribute("dailyEvents", dailyEvents);
        return "home";
    }
}
