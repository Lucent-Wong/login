package com.primedice.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="shiro")
public class ShiroProperties {
    private String hashAlgorithmName;

    private int hashIterations;

    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }
}
