package com.primedice.app.service;


import com.primedice.common.entity.UserAccount;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;


public interface UserAccountService {
    int createWallet(String username) throws Exception;

    Credentials getWallet(String username) throws Exception;

    int disableWallet(String username);

    int enableWallet(String username);

    UserAccount withdraw(String username, Long value, Convert.Unit unit) throws Exception;

    UserAccount syncBalance(String username) throws Exception;

    UserAccount transferToRootAccount(String username) throws Exception;
}
