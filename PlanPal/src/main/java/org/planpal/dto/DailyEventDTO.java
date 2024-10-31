package org.planpal.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.sql.Date;

@Setter
@Getter
public class DailyEventDTO {
    private int eventId;
    private int userId;
    private int groupId;
    private String title;
    private Date eventDate;
    private String location;
    private String description;
    private String category;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
