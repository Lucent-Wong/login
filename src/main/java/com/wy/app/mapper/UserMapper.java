package com.wy.app.mapper;

import com.wy.app.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    User findById(Long id);

    User findByUsername(String username);

    int createUser(User user);

    int updateUser(User user);

    int deleteById(Long id);

    int deleteByUsername(String username);

    void mapToRole(Long userId, Long roleId);

    void unmapFromRole(Long userId, List roleId);

    void unmapFromAllRoles(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

}
