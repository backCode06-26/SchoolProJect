package com.recycle.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {
    String email;
    Timestamp joinDate;
    int gameScore;
}
