package com.api.nursery_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.nursery_system.dto.UserDto;
import com.api.nursery_system.entity.Role;
import com.api.nursery_system.entity.User;
import com.api.nursery_system.entity.Venture;
import com.api.nursery_system.exception.InvalidCredentialsException;
import com.api.nursery_system.exception.ResourceNotFoundException;
import com.api.nursery_system.repository.RoleRepository;
import com.api.nursery_system.request.CreateUserRequest;
import com.api.nursery_system.request.UserLoginRequest;
import com.api.nursery_system.response.ApiResponse;
import com.api.nursery_system.service.user.IUserService;
import com.api.nursery_system.service.venture.IVentureService;
import com.api.nursery_system.util.Constants;
import com.api.nursery_system.util.HelperMethods;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {



    private final IUserService userService;
    private final IVentureService ventureService;
    private final RoleRepository roleRepository;

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    /**
     * Create a new user.
     *
     * @param user The user data (validated).
     * @return ApiResponse with created user details.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            Role userRole = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid User Role"));
            User newUser = new User();
           
            newUser.setUserName(request.getUserName());
            newUser.setEmailId(request.getEmailId());
            newUser.setPassword(request.getPassword());
            newUser.setContactNo(request.getContactNo());
            newUser.setAddress(request.getAddress());
            newUser.setRole(userRole);
            if (userRole.getRoleId() == 1L) {
                String tenantId = HelperMethods.generateTenantId();
                newUser.setTenantId(tenantId);
                
                User createdUser = userService.createUser(newUser);
                Venture newVenture = request.getVentureDetails();
                newVenture.setUserId(createdUser.getUserId());
                ventureService.createVenture(newVenture);
                createTenantSchema(tenantId);
            }else{
                newUser.setTenantId("public");
                userService.createUser(newUser);
            }
            ApiResponse response = new ApiResponse(Constants.SUCCESS_STATUS, "User created successfully",newUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, "Failed to create user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Update an existing user.
     *
     * @param userId The ID of the user to update.
     * @param user   The updated user data (validated).
     * @return ApiResponse with updated user details.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(userId, user);
            ApiResponse response = new ApiResponse(Constants.SUCCESS_STATUS, "User updated successfully", updatedUser);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, "Failed to update user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Delete a user by ID.
     *
     * @param userId The ID of the user to delete.
     * @return ApiResponse with deletion confirmation.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            ApiResponse response = new ApiResponse(Constants.SUCCESS_STATUS, "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, "Failed to delete user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get user details by ID.
     *
     * @param userId The ID of the user.
     * @return ApiResponse with user details.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            ApiResponse response = new ApiResponse(Constants.SUCCESS_STATUS, user);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, "Failed to fetch user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get all users.
     *
     * @return ApiResponse with list of all users.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            ApiResponse response = new ApiResponse(Constants.SUCCESS_STATUS, users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, "Failed to fetch users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Login endpoint.
     *
     * @param userName Username as request parameter.
     * @param password Password as request parameter.
     * @return ApiResponse with UserDto if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest request) {
        try {
            UserDto userDto = userService.login(request.getEmailId(), request.getPassword());
            ApiResponse response = new ApiResponse(Constants.SUCCESS_STATUS, userDto);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException ex) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, "Failed to login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Exception handler for validation errors.
     * This method captures MethodArgumentNotValidException and returns a meaningful
     * error message.
     *
     * @param ex The validation exception.
     * @return ApiResponse with error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ApiResponse response = new ApiResponse(Constants.ERROR_STATUS, errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private void createTenantSchema(String schema) {
        // Create the schema if it doesn't exist
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS " + schema);

        // Run Flyway migrations for the new tenant schema.
        // Assumes that migration scripts are located under src/main/resources/db/migration/tenant
        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .schemas(schema)
            .locations("classpath:db/migration/tenant")
            .load();
        flyway.migrate();
    }
}
