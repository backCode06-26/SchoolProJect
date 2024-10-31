package org.planpal.domain;

import lombok.Getter;
import lombok.Setter;
import org.planpal.eunm.UserType;

import java.sql.Timestamp;

@Setter
@Getter
public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;
    private UserType userType;
}
