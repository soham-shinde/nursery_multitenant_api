package com.api.nursery_system.service.role;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.nursery_system.entity.Role;
import com.api.nursery_system.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    
    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long roleId, Role role) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
        existingRole.setRoleName(role.getRoleName());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

