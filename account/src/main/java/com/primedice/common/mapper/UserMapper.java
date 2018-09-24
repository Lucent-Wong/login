package com.primedice.common.mapper;

import com.primedice.common.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Set;

@Mapper
public interface UserMapper {

    User findById(Long id);

    User findByUsername(String username);

    int createUser(User user);

    int updateUser(User user);

    int deleteById(Long id);

    int deleteByUsername(String username);

    void mapToRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    void unmapFromRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    void unmapFromAllRoles(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

}
