package com.wy.app.service;

import com.wy.app.entity.User;
import com.wy.app.mapper.UserMapper;
import com.wy.app.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public Long createUser(User user) {
        passwordUtil.encryptPassword(user);
        userMapper.createUser(user);
        return user.getId();
    }

    @Override
    public void deleteUserByUsername(String username) {
        userMapper.deleteByUsername(username);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {

    }

    @Override
    @Transactional
    public void mapToRoles(Long userId, Long... roleIds) {
        Arrays.stream(roleIds).forEach(roleId -> userMapper.mapToRole(userId, roleId));
    }

    @Override
    @Transactional
    public void unmapFromRoles(Long userId, Long... roleIds) {
        Arrays.stream(roleIds).forEach(roleId -> userMapper.unmapFromRole(userId, roleId));
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByUsernameWithoutSecret(String username) {
        return findByUsername(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        return userMapper.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return userMapper.findPermissions(username);
    }

}
