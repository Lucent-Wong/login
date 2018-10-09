package com.primedice.app.auth;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static com.primedice.app.constants.Constant.CAPTCHA_CODE;
import static org.apache.shiro.web.filter.authc.FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

@Getter
@Setter
public class CaptchaAccessControlFilter extends AccessControlFilter {
    private boolean captchaEnabled = true;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        request.setAttribute("captchaEbabled", captchaEnabled);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        if (captchaEnabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        Session session = SecurityUtils.getSubject().getSession();
        String captchaCode = getCaptchaCode(request);
        String validateCode = (String)session.getAttribute(CAPTCHA_CODE);

        if(captchaCode == null) {
            return false;
        } else if (validateCode != null) {
            captchaCode = captchaCode.toLowerCase();
            validateCode = validateCode.toLowerCase();
            return captchaCode.equals(validateCode);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "captcha.error");
        return true;
    }

    public String getCaptchaCode(ServletRequest request) {
        return WebUtils.getCleanParam(request, CAPTCHA_CODE);
    }
}