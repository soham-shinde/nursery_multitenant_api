package com.api.nursery_system.dto;

import com.api.nursery_system.entity.User;
import com.api.nursery_system.entity.Venture;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String userName;
    private String emailId;
    private String contactNo;
    private String address;
    private String tenantId;
    private String roleName;
    private Venture ventureDetails;

    public static UserDto from(User user) {
        UserDto dto = new UserDto();
        dto.userId = user.getUserId();
        dto.userName = user.getUserName();
        dto.emailId = user.getEmailId();
        dto.contactNo = user.getContactNo();
        dto.address = user.getAddress();
        dto.tenantId = user.getTenantId();
        dto.roleName = user.getRole() != null ? user.getRole().getRoleName() : null;
        return dto;
    }
}
