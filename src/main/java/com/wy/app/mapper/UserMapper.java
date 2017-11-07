package com.wy.app.mapper;

import com.wy.app.entity.User;
import com.wy.app.mapper.SqlProvider.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.Set;

@Mapper
public interface UserMapper {

    @Select("select id, username, locked, service_call_limit serviceCallLimit from sys_users u where u.id = #{id}")
    User findOne(Long userId);

    @Select("select id, username, locked, service_call_limit serviceCallLimit from sys_users u where u.username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO SYS_USERS (username, password, salt, service_call_limit, locked) " +
            "VALUES (#{username}, #{password}, #{salt}, #{serviceCallLimit}, #{locked})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createUser(User user);

    @UpdateProvider(type = UserProvider.class, method = "updateUser")
    void updateUser(User user);


    public void deleteUser(Long userId);
    public int setServiceCallLimit(String username);

    public void deleteUserByUsername(String username);

    public void correlationRoles(Long userId, Long... roleIds);
    public void uncorrelationRoles(Long userId, Long... roleIds);
    public void uncorrelationAllRoles(String username);

    public Set<String> correlationServices(Long userId, String... services);
    public void uncorrelationServices(Long userId, String... services);


    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

    Set<String> findServicesByUsername(String userName);
    Set<String> findServices(Long userId);
    int getServiceNum(Long userId);
    int getAllServiceNum();
    int getServiceTotalByUsername(String username);
    int getServiceCallTotalByUsername(String username);
    Set<String> findServices(Long userId, String serviceId);
    Set<String> findServices(Long userId, int pageSize, int page);
    Set<String> findAllServices(int pageSize, int page);
    Set<String> findAllServices();
    String getUserNameByServiceID(String serviceId);
    int consumeServiceCall(String serviceId);
}
