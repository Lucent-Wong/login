package com.primedice.app.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import static com.primedice.app.constants.Constant.*;
import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

@Api("login controller")
@Controller
@RequestMapping(value = "/")
@Slf4j
@Setter
public class LoginController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value="authenticate", notes = "user auth")
    @PostMapping(value = "authenticate")
    public String authenticate(HttpServletRequest request, String username, String password,
                               RedirectAttributes redirectAttributes) {
        String error = null;
        try {
            if (request.getAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME) != null) {
                error = "Captcha wrong";
                redirectAttributes.addFlashAttribute(LOGIN_STATUS,BAD_REQUEST_CODE);
            } else {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
                redirectAttributes.addFlashAttribute(LOGIN_STATUS, SUCCESS_RESPONSE_CODE);
                return "redirect:/index";
            }
        } catch (UnknownAccountException e) {
            error = "Username/password";
            redirectAttributes.addFlashAttribute(LOGIN_STATUS, LOGIN_FAIL_CREDENTIAL_WRONG);
        } catch (IncorrectCredentialsException e) {
            error = "Username/password";
            redirectAttributes.addFlashAttribute(LOGIN_STATUS, LOGIN_FAIL_CREDENTIAL_WRONG);
        } catch (ExcessiveAttemptsException e) {
            error = "Failed too many times";
            redirectAttributes.addFlashAttribute(LOGIN_STATUS, TOO_MANY_LOGIN_TIMES);
        } catch (AuthenticationException e) {
            log.error("internal error when login", e);
            error = "login fail";
            redirectAttributes.addFlashAttribute(LOGIN_STATUS, LOGIN_FAIL);
        }
        if (error != null) {
            redirectAttributes.addFlashAttribute(ERROR_MSG, error);
            log.error(error);
        }
        return "redirect:/login";
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

    @ApiOperation(value = "captcha", notes = "get captcha")
    @GetMapping(value = "captcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute(CAPTCHA_CODE, createText);
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            log.error("error when generate kaptcha", e);
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

}
