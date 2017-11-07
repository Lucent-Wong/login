package com.wy.app.service;

import com.wy.app.entity.User;

import java.util.Set;

public interface UserService {
    /**
     * 创建用户
     * @param user
     */
    public User createUser(User user);

    /**
     * 刪除用戶
     * @param username
     */
    public void deleteUserByUsername(String username);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(Long userId, Long... roleIds);


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(Long userId, Long... roleIds);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 查找無密碼用戶信息
     * @param username
     * @return
     */
    public User findByUsernameWithoutSecret(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);

    /**
     * 添加用戶-服務關係
     * @param userId
     * @param services
     * @return
     */
    public Set<String> correlationServices(Long userId, String... services);

    /**
     * 移除用戶-服務關係
     * @param userId
     * @param services
     */
    public void uncorrelationServices(Long userId, String... services);

    /**
     * 根據用戶名查找服務
     * @param userName
     * @return
     */
    public Set<String> findServicesByUsername(String userName);

    /**
     * 根據用戶id查找服務
     * @param userId
     * @return
     */
    public Set<String> findServices(Long userId);

    /**
     * 根據userid和service id
     * @param userId
     * @param  serviceId
     * @return
     */
    public Set<String> findServices(Long userId, String serviceId);

    /**
     * 分頁查詢服務
     * @param userId
     * @param pageSize
     * @param page
     * @return
     */
    public Set<String> findServices(Long userId, int pageSize, int page);

    /**
     * 分頁查詢所有服務
     * @param pageSize
     * @param page
     * @return
     */
    public Set<String> findAllServices(int pageSize, int page);

    /**
     * 查詢所有服務
     * @return
     */
    public Set<String> findAllServices();

    /**
     * 查詢服務數量
     * @param userId
     * @return
     */
    public int getServiceNum(Long userId);
    public int getAllServiceNum();
    public int getServiceTotalByUsername(String username);
    public int getAllServiceCallTotalByUsername(String username);
    public int consumeServiceCall(String id);
    public int setServiceCallLimit(String username);
    public String getUserNameByServiceID(String serviceID);
}
