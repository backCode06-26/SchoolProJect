package org.planpal.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class EventGroup {
    private int groupId;
    private int userId;
    private String groupName;
    private String description;
    private String category;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
