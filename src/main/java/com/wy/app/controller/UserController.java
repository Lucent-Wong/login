package com.wy.app.controller;

import com.wy.app.entity.User;
import com.wy.app.service.UserService;
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
    public User get(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @RequestMapping(value = "getPermissions/{username}", method = RequestMethod.GET, produces = "application/json")
    public Set<String> getPermissions(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        return userService.findPermissions(username);
    }

    @RequestMapping(value = "create/{username}", method = RequestMethod.GET, produces = "application/json")
    public Long create(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "username") String username) {
        User user = new User();
        user.setUsername(username);
        user.setLocked(false);
        return userService.createUser(user);
    }
}
