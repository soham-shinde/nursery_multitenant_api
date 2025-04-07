package com.api.nursery_system.service.role;

import java.util.List;

import com.api.nursery_system.entity.Role;

public interface IRoleService {
    Role createRole(Role role);

    Role updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);

    Role getRoleById(Long roleId);

    List<Role> getAllRoles();
}
