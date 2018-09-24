package com.primedice.common.controller;

import com.primedice.common.service.UserService;
import com.primedice.common.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET, produces = "application/json")
    public User get(HttpServletRequest request, HttpServletResponse response, @PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Long create(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setLocked(user.getLocked());
        return userService.createUser(newUser);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public int update(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setLocked(user.getLocked());
        return userService.updateUser(newUser);
    }

    @RequestMapping(value = "{username}", method = RequestMethod.DELETE, produces = "application/json")
    public int create(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }
}
