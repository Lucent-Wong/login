package com.primedice.common.mapper;

import com.primedice.common.entity.Permission;

public interface PermissionMapper {
    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
