package com.primedice.app.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.web3j.crypto.*;

import java.io.IOException;

public class WalletUtils extends org.web3j.crypto.WalletUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Credentials loadCredentialsByContent(@NonNull String password, @NonNull String content)
            throws IOException, CipherException {
        WalletFile walletFile = objectMapper.readValue(content, WalletFile.class);
        return Credentials.create(Wallet.decrypt(password, walletFile));
    }
}
