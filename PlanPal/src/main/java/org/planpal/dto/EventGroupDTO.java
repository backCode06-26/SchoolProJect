package org.planpal.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class EventGroupDTO {
    private int groupId;
    private int userId;
    private String groupName;
    private String description;
    private String category;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
