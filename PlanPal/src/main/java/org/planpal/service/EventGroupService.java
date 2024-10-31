package org.planpal.service;

import org.planpal.domain.EventGroup;
import org.planpal.dto.EventGroupDTO;
import org.planpal.repository.EventGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventGroupService {
    private final EventGroupRepository eventGroupRepository;

    @Autowired
    public EventGroupService(EventGroupRepository eventGroupRepository) {
        this.eventGroupRepository = eventGroupRepository;
    }

    /**
     * 새로운 이벤트 그룹을 저장합니다.
     */
    public int saveEventGroup(EventGroupDTO eventGroupDTO) {

        EventGroup eventGroup = convertToEntity(eventGroupDTO);
        return eventGroupRepository.saveEventGroup(eventGroup);
    }

    /**
     * 기존 이벤트 그룹을 업데이트합니다.
     */
    public void updateEventGroup(EventGroupDTO eventGroupDTO) {
        EventGroup eventGroup = convertToEntity(eventGroupDTO);
        eventGroupRepository.updateEventGroup(eventGroup);
    }

    /**
     * 특정 이벤트 그룹을 삭제합니다.
     */
    public void deleteEventGroup(int groupId) {
        eventGroupRepository.deleteEventGroup(groupId);
    }

    /**
     * 특정 사용자 ID, 페이지 번호, 정렬 기준, 정렬 방향을 사용하여 이벤트 그룹 목록을 가져옵니다.
     */
    public List<EventGroupDTO> getEventGroupsByEventIdDesc(int userId, int page) {
        return eventGroupRepository.getEventGroupsByEventIdDesc(userId, page);
    }

    public List<EventGroupDTO> getEventGroupsByCreatedAtDesc(int page) {
        return eventGroupRepository.getEventGroupsByCreatedAtDesc(page);
    }

    public List<EventGroupDTO> getEventGroupsByCategoryAsc(int page) {
        return eventGroupRepository.getEventGroupsByCategoryAsc(page);
    }

    public List<EventGroupDTO> getEventGroupsByRatingDesc(int page) {
        return eventGroupRepository.getEventGroupsByRatingDesc(page);
    }

    public int getUserIdByGroupId(int groupId) {
        return eventGroupRepository.getUserIdByGroupId(groupId);
    }

    /**
     * 특정 사용자에 대한 총 이벤트 그룹 수를 반환합니다.
     */
    public int getTotalEventGroupsCount(int userId) {
        return eventGroupRepository.getTotalEventGroupsCount(userId);
    }

    /**
     * 모든 이벤트 그룹의 총 수를 반환합니다.
     */
    public int getTotalEventGroupsCount() {
        return eventGroupRepository.getTotalEventGroupsCount();
    }

    /**
     * 특정 사용자에 대한 총 페이지 수를 반환합니다.
     */
    public int getTotalPageCount(int userId) {
        return eventGroupRepository.getTotalPageCount(userId);
    }

    /**
     * 전체 이벤트 그룹에 대한 총 페이지 수를 반환합니다.
     */
    public int getTotalPageCount() {
        return eventGroupRepository.getTotalPageCount();
    }

    // DTO를 엔티티로 변환합니다.
    private EventGroup convertToEntity(EventGroupDTO dto) {
        EventGroup eventGroup = new EventGroup();
        eventGroup.setGroupId(dto.getGroupId());
        eventGroup.setUserId(dto.getUserId());
        eventGroup.setGroupName(dto.getGroupName());
        eventGroup.setDescription(dto.getDescription());
        eventGroup.setCategory(dto.getCategory());
        eventGroup.setCreatedAt(dto.getCreatedAt());
        eventGroup.setUpdatedAt(dto.getUpdatedAt());
        return eventGroup;
    }
}
