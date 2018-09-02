package com.wy.app.mapper;

import com.wy.app.entity.Permission;

public interface PermissionMapper {
    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
