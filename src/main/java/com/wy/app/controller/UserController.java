package com.wy.app.controller;

import com.wy.app.entity.User;
import com.wy.app.service.UserService;
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

    @RequestMapping(value = "get/{username}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public User get(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "username") String username) {
        System.out.println("hello");
        return userService.findByUsername(username);
    }
}
