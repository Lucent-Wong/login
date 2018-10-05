package com.primedice.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="web3")
@Getter
@Setter
public class Web3Properties {
    private String url;
    private String filePath;
    private String extractRate;
    private String rootTransferThreshold;

    public Long getRawExtractRate() {
        return Long.valueOf(extractRate);
    }

    public Long getRawRootTransferThreshold() {
        return Long.valueOf(rootTransferThreshold);
    }
}
