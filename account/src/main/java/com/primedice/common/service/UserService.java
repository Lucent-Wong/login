package com.primedice.common.service;

import com.primedice.common.entity.User;

import java.util.Set;

public interface UserService {
    /**
     * 创建用户
     * @param user
     */
    public Long createUser(User user);

    /**
     * 刪除用戶
     * @param username
     */
    public int deleteUserByUsername(String username);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);

    /**
     * 修改用户
     * @param user
     */
    public int updateUser(User user);

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void mapToRoles(Long userId, Long... roleIds);


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void unmapFromRoles(Long userId, Long... roleIds);

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

}
