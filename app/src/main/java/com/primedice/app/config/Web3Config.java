package com.primedice.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

@Configuration
@Slf4j
public class Web3Config {

    @Bean
    public Web3j web3j(Web3Properties web3Properties) throws IOException {
        Web3j web3j = Web3j.build(new HttpService(web3Properties.getUrl()));
        log.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        return web3j;
    }
}
