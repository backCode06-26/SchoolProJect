package org.planpal.controller;

import org.planpal.dto.EventGroupDTO;
import org.planpal.service.DailyEventService;
import org.planpal.service.EventGroupService;
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
@RequestMapping("/group")
public class GroupController {

    private final EventGroupService eventGroupService;

    @Autowired
    public GroupController(EventGroupService eventGroupService, DailyEventService dailyEventService) {
        this.eventGroupService = eventGroupService;
    }

    private int getUserIdFromSession() {
        String session = SecurityContextHolder.getContext().getAuthentication().getName();
        return Integer.parseInt(session);
    }

    private int getPlanUserIdFromSession(int groupId) {
        return eventGroupService.getUserIdByGroupId(groupId);
    }

    /**
     * 새 이벤트 그룹 생성 페이지를 반환합니다.
     */
    @GetMapping("/new")
    public String planCreate() {
        return "groupCreate";
    }

    /**
     * 새로운 이벤트 그룹을 생성합니다.
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createSchedule(@RequestBody EventGroupDTO eventGroupDTO) {
        int userId = getUserIdFromSession();
        eventGroupDTO.setUserId(userId);

        int groupId = eventGroupService.saveEventGroup(eventGroupDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "일정이 성공적으로 생성되었습니다.");
        response.put("groupId", groupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 기존 이벤트 그룹을 업데이트합니다.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateSchedule(@PathVariable int id,
                                                              @RequestBody EventGroupDTO eventGroupDTO) {
        eventGroupDTO.setGroupId(id);
        eventGroupService.updateEventGroup(eventGroupDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "성공적으로 업데이트되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 특정 이벤트 그룹을 삭제합니다.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteSchedule(@PathVariable int id) {
        eventGroupService.deleteEventGroup(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "성공적으로 삭제되었습니다.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 ID와 페이지 번호를 기준으로 이벤트 그룹 목록을 조회합니다.
     */
    @GetMapping("/view")
    public String planView(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int userId = getUserIdFromSession(); // 세션에서 사용자 ID 가져오기

        // 페이지에 맞는 이벤트 데이터를 가져옴
        List<EventGroupDTO> events = eventGroupService.getEventGroupsByEventIdDesc(userId, page);
        int totalPageCount = eventGroupService.getTotalPageCount(userId);

        model.addAttribute("isOwner", true);
        model.addAttribute("path", "/group/view");
        model.addAttribute("events", events);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPageCount", totalPageCount);

        return "groupView";
    }

    /**
     * 평점의 평균의 내림차순으로 정렬된 이벤트 그룹을 조회합니다.
     */
    @GetMapping("/view/rating")
    public String planViewRating(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int userId = getUserIdFromSession(); // 세션에서 사용자 ID 가져오기

        // 페이지에 맞는 이벤트 데이터를 가져옴
        List<EventGroupDTO> events = eventGroupService.getEventGroupsByRatingDesc(page);
        int totalPageCount = eventGroupService.getTotalPageCount(userId);

        model.addAttribute("isOwner", false);
        model.addAttribute("path", "/group/view/rating");
        model.addAttribute("events", events);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPageCount", totalPageCount);
        return "groupView";
    }

    /**
     * 최근 생성된 이벤트 그룹 목록을 조회합니다.
     */
    @GetMapping("/view/latest")
    public String planViewLatest(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int userId = getUserIdFromSession();

        // 최근 생성된 이벤트 그룹 목록을 가져옴
        List<EventGroupDTO> events = eventGroupService.getEventGroupsByCreatedAtDesc(page);
        int totalPageCount = eventGroupService.getTotalPageCount(userId);

        model.addAttribute("isOwner", false);
        model.addAttribute("path", "/group/view/latest");
        model.addAttribute("events", events);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPageCount", totalPageCount);

        return "groupView";
    }

    /**
     * 카테고리별 오름차순으로 정렬된 이벤트 그룹 목록을 조회합니다.
     */
    @GetMapping("/view/category")
    public String planViewGrade(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int userId = getUserIdFromSession();

        // 카테고리별 오름차순으로 정렬된 이벤트 그룹 목록을 가져옴
        List<EventGroupDTO> events = eventGroupService.getEventGroupsByCategoryAsc(page);
        int totalPageCount = eventGroupService.getTotalPageCount(userId);

        model.addAttribute("isOwner", false);
        model.addAttribute("path", "/group/view/grade");
        model.addAttribute("events", events);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPageCount", totalPageCount);

        return "groupView";
    }
}
