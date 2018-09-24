package com.primedice.common.mapper;

import com.primedice.common.entity.Role;

import java.util.Set;

public interface RoleMapper {
    Role createRole(Role role);
    void updateRole(Role role);
    void deleteRole(Long roleId);

    void mapToPermission(Long roleId, Long permissionId);
    void unmapFromPermission(Long roleId, Long permissionId);

    Role findById(Long roleId);
    Role findByName(String roleName);
    Set<String> findUsers(String roleName);
    Set<String> findPermissions(String RoleName);
}
