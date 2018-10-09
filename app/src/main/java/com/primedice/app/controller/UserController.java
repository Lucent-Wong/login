package com.primedice.app.controller;

import com.primedice.app.service.UserAccountService;
import com.primedice.app.service.UserService;
import com.primedice.common.entity.User;
import com.primedice.common.entity.UserAccount;
import com.primedice.common.entity.WithdrawRequest;
import com.primedice.common.exceptions.InternalErrorException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("user controller")
@RestController
@RequestMapping(value = "/users")
@Setter
@Getter
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountService userAccountService;


    @ApiOperation(value="get", notes = "get user")
    @GetMapping(produces = "application/json")
    public User get(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        return userService.findByUsername(username);
    }

    @ApiOperation(value="create", notes = "create user")
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public void create(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) throws Exception {
        User newUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .locked(user.getLocked())
                .build();
        userService.createUser(newUser);
        try {
            userAccountService.createWallet(user.getUsername());
        } catch (Exception e) {
            log.error("error when create wallet", e);
            throw new InternalErrorException("error when create user account");
        }
    }

    @ApiOperation(value="update", notes = "update user")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User newUser = User.builder()
                .username(username)
                .password(user.getPassword())
                .locked(user.getLocked())
                .build();
        userService.updateUser(newUser);
    }


    /**** admin methods *****/
    @ApiOperation(value="delete user", notes = "delete user")
    @DeleteMapping(value = "{username}", produces = "application/json")
    @RequiresRoles(value = "root")
    public void delete(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        userAccountService.disableWallet(username);
    }

    @ApiOperation(value="get any user", notes = "get any user")
    @GetMapping(value = "{username}", produces = "application/json")
    @RequiresRoles(value = "root")
    public User getUser(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        return userService.findByUsername(username);
    }

    @ApiOperation(value="update any user", notes = "update any user")
    @PutMapping(value = "{username}", consumes = "application/json", produces = "application/json")
    @RequiresRoles(value = "root")
    public User updateUser(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String username, @RequestBody User user) {
        User newUser = User.builder()
                .username(username)
                .password(user.getPassword())
                .locked(user.getLocked())
                .build();
        userService.updateUser(newUser);
        return newUser;
    }
}
