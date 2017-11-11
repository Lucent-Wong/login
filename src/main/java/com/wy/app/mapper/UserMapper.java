package com.wy.app.mapper;

import com.wy.app.entity.User;
import com.wy.app.mapper.SqlProvider.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    @Select("select id, username, locked, service_call_limit serviceCallLimit from sys_users u where u.id = #{id}")
    User findByUserId(Long userId);

    @Select("select id, username, locked, service_call_limit serviceCallLimit from sys_users u where u.username = #{username}")
    User findByUsername(String username);

    @Insert("insert into sys_users (username, password, salt, service_call_limit, locked) " +
            "VALUES (#{username}, #{password}, #{salt}, #{serviceCallLimit}, #{locked})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createUser(User user);

    @UpdateProvider(type = UserProvider.class, method = "updateUser")
    int updateUser(User user);

    @Delete("delete from sys_users u where u.id = #{id}")
    int deleteUser(Long userId);

    @Delete("delete from sys_users u where u.username = #{username}")
    int deleteUserByUsername(String username);

    @InsertProvider(type = UserProvider.class, method = "correlationRoles")
    void correlationRoles(Long userId, List<Long> roleIds);

    @DeleteProvider(type = UserProvider.class, method = "uncorrelationRoles")
    void uncorrelationRoles(Long userId, List<Long>roleIds);

    @Delete("delete from sys_users_roles where username= #{username}")
    void uncorrelationAllRoles(@Param("username") String username);

    @Select("select role from sys_users u, sys_roles r,sys_users_roles ur where u.username= #{username} and u.id=ur.user_id and r.id=ur.role_id")
    Set<String> findRoles(@Param("username") String username);

    @Select("select permission from sys_users u, sys_roles r, sys_permissions p, sys_users_roles ur, sys_roles_permissions rp where u.username= #{username} and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and p.id=rp.permission_id")
    Set<String> findPermissions(@Param("username") String username);

}
