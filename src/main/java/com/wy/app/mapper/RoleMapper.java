package com.wy.app.mapper;

import com.wy.app.entity.Role;

import java.util.Set;

public interface RoleMapper {
    Role createRole(Role role);
    void updateRole(Role role);
    void deleteRole(Long roleId);

    void correlationPermissions(Long roleId, Long... permissionIds);
    void uncorrelationPermissions(Long roleId, Long... permissionIds);

    Role findByRoleId(Long roleId);
    Role findByRoleName(String roleName);
    Set<String> findUsers(String roleName);
    Set<String> findPermissions(String RoleName);
}
