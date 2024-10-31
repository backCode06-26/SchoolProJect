package org.planpal.controller;

import org.planpal.dto.DailyEventDTO;
import org.planpal.dto.ReviewDTO;
import org.planpal.service.DailyEventService;
import org.planpal.service.EventGroupService;
import org.planpal.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/plan")
public class PlanController {
    private final DailyEventService dailyEventService;
    private final EventGroupService eventGroupService;
    private final ReviewService reviewService;

    @Autowired
    public PlanController(DailyEventService dailyEventService, EventGroupService eventGroupService, ReviewService reviewService) {
        this.dailyEventService = dailyEventService;
        this.eventGroupService = eventGroupService;
        this.reviewService = reviewService;
    }

    private int getUserIdFromSession() {
        String session = SecurityContextHolder.getContext().getAuthentication().getName();
        return Integer.parseInt(session);
    }

    private int getPlanUserIdFromSession(int groupId) {
        return eventGroupService.getUserIdByGroupId(groupId);
    }

    @GetMapping("/add/{groupId}")
    public String planAdd(@PathVariable int groupId, Model model) {
        int currentUserId = getUserIdFromSession();
        int planUserId = getPlanUserIdFromSession(groupId);

        model.addAttribute("groupId", groupId);
        model.addAttribute("userId", currentUserId);
        if(planUserId == currentUserId) {
            return "planCreate"; // Thymeleaf 템플릿 이름
        } else {
            List<ReviewDTO> comments = reviewService.getReviewsByGroupId(groupId);
            model.addAttribute("comments", comments);
            return "planView";
        }
    }

    @GetMapping("/get/event/{groupId}")
    public ResponseEntity<List<DailyEventDTO>> getEvents(@PathVariable int groupId) {
        List<DailyEventDTO> events = dailyEventService.getDailyEventsByGroupId(groupId);
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }


    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveSchedule(@RequestBody List<DailyEventDTO> dailyEventDTOs) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "일정이 성공적으로 생성되었습니다.");
        response.put("events", new HashMap<String, Integer>()); // 'events' 항목 초기화

        try {
            if(!dailyEventDTOs.isEmpty()) {
                dailyEventService.delete(dailyEventDTOs.get(0).getGroupId());
            }
            for (DailyEventDTO dailyEventDTO : dailyEventDTOs) {
                int eventId = dailyEventService.create(dailyEventDTO);

                // 'events' 항목을 'Map<String, Integer>'로 가져와서 값을 넣기
                ((Map<String, Integer>) response.get("events")).put(dailyEventDTO.getTitle(), eventId);
            }
        } catch (Exception e) {
            // 로그를 남기거나 상세한 에러 메시지를 추가할 수 있습니다.
            e.printStackTrace();
            response.put("message", "일정 생성 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
