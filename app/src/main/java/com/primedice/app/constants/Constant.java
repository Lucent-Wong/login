package com.primedice.app.constants;

public interface Constant {
    String ROOT_ACCOUNT_NAME = "root";
    String CAPTCHA_CODE = "kaptchaCode";

    String STATUS_SUCCESS = "success";
    String STATUS_FAIL = "fail";

    Integer SUCCESS_RESPONSE_CODE = 200;
    Integer BAD_REQUEST_CODE = 400;
    Integer INTERNAL_ERROR_CODE = 500;
    Integer UNAUTHORIZE_RESPONSE_CODE = 401;
    Integer UNAUTHENTICATE_RESPONSE_CODE = 403;


    Integer INITIAL_LOGIN = 1000;
    Integer LOGIN_FAIL_CREDENTIAL_WRONG = 1001;
    Integer TOO_MANY_LOGIN_TIMES = 1002;
    Integer LOGIN_FAIL = 1003;
    Integer NEED_CAPTCHA = 1004;

    String LOGIN_STATUS = "loginStatus";
    String ERROR_MSG = "errorMsg";
}
