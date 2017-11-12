package com.wy.app.util;

import com.wy.app.config.ShiroProperties;
import com.wy.app.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Autowired
    private ShiroProperties shiroProperties;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setShiroProperties(ShiroProperties shiroProperties) {
        this.shiroProperties = shiroProperties;
    }

    public void encryptPassword(User user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                shiroProperties.getHashAlgorithmName(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                shiroProperties.getHashIterations()).toHex();

        user.setPassword(newPassword);
    }
}