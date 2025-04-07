package com.api.nursery_system.service.user;

import java.util.List;

import com.api.nursery_system.dto.UserDto;
import com.api.nursery_system.entity.User;

public interface IUserService {
    User createUser(User user);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    User getUserById(Long userId);
    List<User> getAllUsers();
    UserDto login(String userName, String password);
}
