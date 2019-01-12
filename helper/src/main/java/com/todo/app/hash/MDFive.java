package com.todo.app.hash;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MDFive {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDFive.class);

    public String getHash(final String data) {
        StringBuilder hashText = null;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            final byte[] array = messageDigest.digest(
                    data.getBytes(StandardCharsets.UTF_8));
            final BigInteger coder = new BigInteger(1, array);
            hashText = new StringBuilder(coder.toString(16));
            while (hashText.length() < 32) {
                hashText.append('0');
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn(e.getMessage());
        }
        return hashText == null ? null : hashText.toString();
    }

}
