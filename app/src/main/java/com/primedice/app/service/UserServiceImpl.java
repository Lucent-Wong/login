package com.primedice.app.service;

import com.primedice.common.entity.User;
import com.primedice.app.mapper.UserMapper;
import com.primedice.app.util.PasswordUtil;
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
    public int deleteUserByUsername(String username) {
        return userMapper.deleteByUsername(username);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {

    }

    @Override
    public int updateUser(User user) {
        if (user.getPassword() !=  null) {
            passwordUtil.encryptPassword(user);
        }
        return userMapper.updateUser(user);
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
