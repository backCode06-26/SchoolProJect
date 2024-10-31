package org.planpal.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class ReviewDTO {
    private int reviewId;
    private int groupId;
    private int userId;
    private int rating;
    private String review;
    private Timestamp createdAt;
}
