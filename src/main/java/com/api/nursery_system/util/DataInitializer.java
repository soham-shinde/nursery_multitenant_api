package com.api.nursery_system.util;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.api.nursery_system.entity.Role;
import com.api.nursery_system.entity.User;
import com.api.nursery_system.repository.RoleRepository;
import com.api.nursery_system.repository.UserRepository;
import com.api.nursery_system.service.role.IRoleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    final RoleRepository roleRepository;
    final UserRepository userRepository;
    final IRoleService roleService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createRoleIfNotExists();
        createUsersIfNotExists();
    }

    private void createRoleIfNotExists() {
        String[] roles = { "VENTURE", "ADMIN", };

        for (String roleName : roles) {
            String role = "ROLE_" + roleName;

            if (roleRepository.existsByRoleName(role)) {
                continue;
            }

            Role roleEntity = new Role();
            roleEntity.setRoleName(role);
            roleRepository.save(roleEntity);
            System.out.println("Role created: " + role);
        }
    }

    private void createUsersIfNotExists() {

        User user = new User();
        user.setUserName("Admin");
        user.setUserName("admin");
        user.setEmailId("admin@gmail.com");
        user.setPassword("admin123");
        user.setContactNo("987456321");
        user.setAddress("Pune");
        user.setTenantId("public");
        user.setRole(roleService.getRoleById(2L));

        if (userRepository.existsByEmailId(user.getEmailId())) {
            return;
        }

        userRepository.save(user);

        System.out.println("User created: " + user.getUserName());

    }

}
