package com.primedice.app.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("login controller")
@Controller
@RequestMapping(value = "/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value="authenticate", notes = "user auth")
    @PostMapping(value = "authenticate")
    public String authenticate(HttpServletRequest request, String username, String password,
                               RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        return "redirect:/index";
    }

    @ApiOperation(value="login", notes = "login page")
    @GetMapping(value = "login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @ApiOperation(value="index", notes = "index page")
    @GetMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
}
