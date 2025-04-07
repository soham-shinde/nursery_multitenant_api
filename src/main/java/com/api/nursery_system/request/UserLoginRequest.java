package com.api.nursery_system.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String emailId;
    private String password;
}
