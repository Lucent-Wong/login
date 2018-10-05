package com.primedice.app.controller;

import com.primedice.app.service.UserAccountService;
import com.primedice.app.service.UserService;
import com.primedice.common.entity.User;
import com.primedice.common.entity.UserAccount;
import com.primedice.common.entity.WithdrawRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.utils.Convert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public User get(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        return userService.findByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void create(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setLocked(user.getLocked());
        userService.createUser(newUser);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void update(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(user.getPassword());
        newUser.setLocked(user.getLocked());
        userService.updateUser(newUser);
    }

    @RequestMapping(value = "withdraw", method = RequestMethod.POST, consumes = "applicatoin/json",
            produces = "application/json")
    @ResponseBody
    public UserAccount withdraw(HttpServletRequest request, HttpServletResponse response,
                                @RequestBody WithdrawRequest withdrawRequest) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        return userAccountService.withdraw(username,
                withdrawRequest.getValue(),
                Convert.Unit.fromString(withdrawRequest.getUnit()));
    }

    @RequestMapping(value = "syncBalance", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserAccount syncBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        return userAccountService.syncBalance(username);
    }

    /**** admin methods *****/
    @RequestMapping(value = "{username}", method = RequestMethod.DELETE, produces = "application/json")
    @RequiresRoles(value = "root")
    public void delete(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        userAccountService.disableWallet(username);
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET, produces = "application/json")
    @RequiresRoles(value = "root")
    public User getUser(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) {
        return userService.findByUsername(username);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @RequiresRoles(value = "root")
    public User updateUser(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String username, @RequestBody User user) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(user.getPassword());
        newUser.setLocked(user.getLocked());
        userService.updateUser(newUser);
        return newUser;
    }
}
