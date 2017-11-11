package com.wy.app.mapper;

import com.wy.app.entity.Permission;

public interface PermissionMapper {
    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);
}
