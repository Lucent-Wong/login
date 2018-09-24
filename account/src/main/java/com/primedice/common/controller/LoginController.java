package com.primedice.common.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public String authenticate(HttpServletRequest request, String username, String password,
                               RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        return "redirect:/index";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "application/json")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET, produces = "application/json")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
}
