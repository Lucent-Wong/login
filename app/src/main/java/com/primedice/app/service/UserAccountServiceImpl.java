package com.primedice.app.service;

import com.primedice.app.config.Web3Properties;
import com.primedice.app.mapper.UserAccountMapper;
import com.primedice.app.util.WalletUtils;
import com.primedice.common.entity.User;
import com.primedice.common.entity.UserAccount;
import com.primedice.common.exceptions.InternalErrorException;
import com.primedice.common.exceptions.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

import static com.primedice.app.constants.Constant.ROOT_ACCOUNT_NAME;

@Slf4j
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Web3Properties properties;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserService userService;

    @Override
    public int createWallet(String username) throws Exception {
        User user = userService.findByUsername(username);
        String secret = getSecret();
        String walletFile = WalletUtils.generateFullNewWalletFile(secret, new File(properties.getFilePath()));
        String walletContent = getWalletContent(walletFile);
        UserAccount userAccount = UserAccount.builder()
                .walletSecret(secret)
                .wallet(walletContent)
                .available(true)
                .deposit(0L)
                .userId(user.getId())
                .build();
        return userAccountMapper.createUserAccount(userAccount);
    }

    @Override
    public Credentials getWallet(String username) throws Exception  {
        User user = userService.findByUsername(username);
        UserAccount userAccount = userAccountMapper.findByUserId(user.getId());
        return getCredentials(userAccount);
    }

    @Override
    @Transactional
    public int disableWallet(String username) {
        userService.updateUser(User.builder().locked(true).build());
        User user = userService.findByUsername(username);
        UserAccount userAccount = UserAccount.builder().available(false).userId(user.getId()).build();
        return userAccountMapper.updateUserAccount(userAccount);
    }

    @Override
    public int enableWallet(String username) {
        userService.updateUser(User.builder().locked(false).build());
        User user = userService.findByUsername(username);
        UserAccount userAccount = UserAccount.builder().available(true).userId(user.getId()).build();
        return userAccountMapper.updateUserAccount(userAccount);
    }

    @Override
    @Transactional
    public UserAccount withdraw(String username, Long value, Convert.Unit unit) throws Exception {
        User rootUser = userService.findByUsername(ROOT_ACCOUNT_NAME);
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new InvalidValueException("user " + username + " not exist");
        }

        UserAccount rootAccount = userAccountMapper.findByUserId(rootUser.getId());
        UserAccount userAccount = userAccountMapper.findByUserIdForUpdate(user.getId());

        //web3j withdraw
        transfer(rootAccount, userAccount, value, unit);

        userAccount = userAccount.withdraw(value);
        userAccountMapper.updateUserAccount(userAccount);
        return userAccount;
    }

    @Override
    @Transactional
    public synchronized UserAccount syncBalance(String username) throws Exception {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new InvalidValueException("user " + username + " not exist");
        }
        UserAccount userAccount = userAccountMapper.findByUserIdForUpdate(user.getId());
        Long expectBalance = userAccount.getEthBalance();

        Credentials credentials = getCredentials(userAccount);
        Long realEthGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send()
                .getBalance()
                .longValueExact();

        Long difference = realEthGetBalance - expectBalance;
        Long rate = properties.getRawExtractRate();
        userAccount.increaseDeposit(extract(difference, rate));
        userAccountMapper.updateUserAccount(userAccount);
        return userAccount;
    }

    @Override
    @Transactional
    public synchronized UserAccount transferToRootAccount(String username) throws Exception {
        User rootUser = userService.findByUsername(ROOT_ACCOUNT_NAME);
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new InvalidValueException("user " + username + " not exist");
        }

        UserAccount rootAccount = userAccountMapper.findByUserId(rootUser.getId());
        UserAccount userAccount = userAccountMapper.findByUserIdForUpdate(user.getId());
        if (userAccount.getEthBalance() < properties.getRawExtractRate()) {
            log.info("The account of " + username + " is too low to transfer");
            return userAccount;
        }

        //web3j withdraw
        Long expectEthBalance = userAccount.getEthBalance();
        transfer(userAccount, rootAccount, expectEthBalance, Convert.Unit.WEI);

        userAccount.decreaseEthBalance(expectEthBalance);
        userAccountMapper.updateUserAccount(userAccount);
        return userAccount;
    }

    private String getSecret() {
        byte[] bytes = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);
        return new String(bytes);
    }

    private String getWalletContent(String fileName) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String path = properties.getFilePath() + "/" + fileName;
        Files.lines(Paths.get(path)).forEach(line -> stringBuffer.append(line));
        return stringBuffer.toString();
    }

    private Credentials getCredentials(UserAccount userAccount) throws IOException, CipherException {
        String walletContent = userAccount.getWallet();
        return WalletUtils.loadCredentialsByContent(userAccount.getWalletSecret(), walletContent);
    }

    private long extract(long money, long rate) throws Exception {
        if (rate > 100) {
            log.error("rate can not larger than 100");
            throw new InternalErrorException("internal error");
        }
        if (Long.MAX_VALUE / 100 < money) {
            throw new InternalErrorException("the value is too large to extract");
        }
        return money * rate / 100L;
    }

    private TransactionReceipt transfer(UserAccount from, UserAccount to, Long value, Convert.Unit unit) throws Exception {
        Credentials credentialsFrom = WalletUtils.loadCredentialsByContent(from.getWalletSecret(),
                from.getWalletSecret());
        Credentials credentialsTo = WalletUtils.loadCredentialsByContent(to.getWalletSecret(),
                to.getWallet());

        //web3j withdraw
        log.info("Sending (" + Convert.fromWei(value.toString(), unit).toPlainString() + " Ether)");
        TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentialsFrom,
                credentialsTo.getAddress(),  // you can put any address here
                new BigDecimal(value), unit)  // 1 wei = 10^-18 Ether
                .send();
        log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());
        return transferReceipt;
    }
}
