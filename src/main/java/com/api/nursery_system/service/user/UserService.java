package com.api.nursery_system.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.nursery_system.dto.UserDto;
import com.api.nursery_system.entity.User;
import com.api.nursery_system.exception.InvalidCredentialsException;
import com.api.nursery_system.exception.ResourceNotFoundException;
import com.api.nursery_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(existingUser);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto login(String userName, String password) {
        User user = userRepository.findByEmailIdAndPassword(userName, password)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
        return UserDto.from(user);
    }

}
