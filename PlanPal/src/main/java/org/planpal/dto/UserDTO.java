package org.planpal.dto;

import lombok.Getter;
import lombok.Setter;
import org.planpal.eunm.UserType;

import java.sql.Timestamp;

@Setter
@Getter
public class UserDTO {
    private int userId;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;
    private UserType userType;
}
