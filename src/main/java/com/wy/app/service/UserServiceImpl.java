package com.wy.app.service;

import com.wy.app.entity.User;
import com.wy.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public User createUser(User user) {
        return userMapper.createUser(user);
    }

    @Override
    public void deleteUserByUsername(String username) {

    }

    @Override
    public void changePassword(Long userId, String newPassword) {

    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {

    }

    @Override
    public void uncorrelationRoles(Long userId, Long... roleIds) {

    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByUsernameWithoutSecret(String username) {
        return null;
    }

    @Override
    public Set<String> findRoles(String username) {
        return null;
    }

    @Override
    public Set<String> findPermissions(String username) {
        return null;
    }

    @Override
    public Set<String> correlationServices(Long userId, String... services) {
        return null;
    }

    @Override
    public void uncorrelationServices(Long userId, String... services) {

    }

    @Override
    public Set<String> findServicesByUsername(String userName) {
        return null;
    }

    @Override
    public Set<String> findServices(Long userId) {
        return null;
    }

    @Override
    public Set<String> findServices(Long userId, String serviceId) {
        return null;
    }

    @Override
    public Set<String> findServices(Long userId, int pageSize, int page) {
        return null;
    }

    @Override
    public Set<String> findAllServices(int pageSize, int page) {
        return null;
    }

    @Override
    public Set<String> findAllServices() {
        return null;
    }

    @Override
    public int getServiceNum(Long userId) {
        return 0;
    }

    @Override
    public int getAllServiceNum() {
        return 0;
    }

    @Override
    public int getServiceTotalByUsername(String username) {
        return 0;
    }

    @Override
    public int getAllServiceCallTotalByUsername(String username) {
        return 0;
    }

    @Override
    public int consumeServiceCall(String id) {
        return 0;
    }

    @Override
    public int setServiceCallLimit(String username) {
        return 0;
    }

    @Override
    public String getUserNameByServiceID(String serviceID) {
        return null;
    }
}
