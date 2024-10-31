package org.planpal.service;

import org.planpal.domain.DailyEvent;
import org.planpal.dto.DailyEventDTO;
import org.planpal.repository.DailyEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DailyEventService {
    private final DailyEventRepository dailyEventRepository;

    @Autowired
    public DailyEventService(DailyEventRepository dailyEventRepository) {
        this.dailyEventRepository = dailyEventRepository;
    }

    public int create(DailyEventDTO dailyEventDTO) {
        DailyEvent dailyEvent = dtoToDomain(dailyEventDTO);
        return dailyEventRepository.saveDailyEvent(dailyEvent);
    }

    public void delete(int groupId) {
        dailyEventRepository.deleteDailyEvent(groupId);
    }

    public ArrayList<DailyEventDTO> getDailyEventsByUserId(int userId) {
        return dailyEventRepository.getDailyEventsByUserId(userId);
    }

    public ArrayList<DailyEventDTO> getDailyEventsByGroupId(int groupId) {
        return dailyEventRepository.getDailyEventsByGroupId(groupId);
    }

    public ArrayList<DailyEventDTO> getDailyEventsWithPagination(int page) {
        return dailyEventRepository.getAllDailyEventsWithPagination(page);
    }

    private DailyEvent dtoToDomain(DailyEventDTO dto) {
        DailyEvent dailyEvent = new DailyEvent();
        dailyEvent.setEventId(dto.getEventId());
        dailyEvent.setUserId(dto.getUserId());
        dailyEvent.setGroupId(dto.getGroupId());
        dailyEvent.setTitle(dto.getTitle());
        dailyEvent.setCategory(dto.getCategory());
        dailyEvent.setDescription(dto.getDescription());
        dailyEvent.setEventDate(dto.getEventDate());
        return dailyEvent;
    }
}
