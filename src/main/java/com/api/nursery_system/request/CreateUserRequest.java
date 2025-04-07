package com.api.nursery_system.request;

import com.api.nursery_system.entity.Venture;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String userName;
    private String emailId;
    private String password;
    private String contactNo;
    private String address;
    private Long roleId;
    private Venture ventureDetails;
}
