package com.primedice.app.controller;

import com.primedice.app.service.UserAccountService;
import com.primedice.common.entity.UserAccount;
import com.primedice.common.entity.WithdrawRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.utils.Convert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("wallet controller")
@RestController
@RequestMapping(value = "/wallet")
@Setter
@Getter
@Slf4j
public class WalletController {

    @Autowired
    private UserAccountService userAccountService;


    @ApiOperation(value="withdraw", notes = "withdraw deposit")
    @PostMapping(value = "withdraw", consumes = "applicatoin/json", produces = "application/json")
    public UserAccount withdraw(HttpServletRequest request, HttpServletResponse response,
                                @RequestBody WithdrawRequest withdrawRequest) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        return userAccountService.withdraw(username,
                withdrawRequest.getValue(),
                Convert.Unit.fromString(withdrawRequest.getUnit()));
    }

    @ApiOperation(value="syncBalance", notes = "sync deposit and balance")
    @GetMapping(value = "syncBalance", produces = "application/json")
    public UserAccount syncBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        return userAccountService.syncBalance(username);
    }

    @ApiOperation(value="getWallet", notes = "get wallet")
    @GetMapping(produces = "application/json")
    public UserAccount getWallet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        return userAccountService.getWalletWithoutCredential(username);
    }

}
