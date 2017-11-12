package com.wy.app.controller;

import com.wy.app.entity.User;
import com.wy.app.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "get/{username}", method = RequestMethod.GET, produces = "application/json")
    @RequiresPermissions("test")
    public User get(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @RequestMapping(value = "getPermissions/{username}", method = RequestMethod.GET, produces = "application/json")
    public Set<String> getPermissions(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        return userService.findPermissions(username);
    }

    @RequestMapping(value = "getRoles/{username}", method = RequestMethod.GET, produces = "application/json")
    public Set<String> getRoles(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        return userService.findRoles(username);
    }

    @RequestMapping(value = "mapToRoles/{username}", method = RequestMethod.GET, produces = "application/json")
    public void mapToRoles(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        userService.mapToRoles(user.getId(), 3L,4L,5L);
    }

    @RequestMapping(value = "unmapFromRoles/{username}", method = RequestMethod.GET, produces = "application/json")
    public void unmapFromRoles(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        userService.unmapFromRoles(user.getId(), 3L,4L,5L);
    }

    @RequestMapping(value = "create/{username}", method = RequestMethod.GET, produces = "application/json")
    public Long create(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "username") String username) {
        User user = new User();
        user.setPassword("hello");
        user.setUsername(username);
        user.setLocked(false);
        return userService.createUser(user);
    }
}
