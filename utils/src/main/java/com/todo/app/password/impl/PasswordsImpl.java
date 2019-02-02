package com.todo.app.password.impl;

import com.todo.app.method.assistant.IsParams;
import com.todo.app.password.IPasswords;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;

public class PasswordsImpl implements IPasswords, Serializable {

    /*Serial version UID*/
    private static final long serialVersionUID = 4749015576472408891L;

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordsImpl.class);

    private static final Random RANDOM = new SecureRandom();

    private static final int SIZE64 = 64;

    private static final int SIZE32 = 32;

    private static final char[] symbols;

    static {
        final StringBuilder tmp = new StringBuilder();
        for (char i = '0'; i <= '9'; i++) {
            tmp.append(i);
        }
        for (char i = 'a'; i <= 'z'; i++) {
            tmp.append(i);
        }
        symbols = tmp.toString().toCharArray();
    }

    @Override
    public byte[] getSalt64() {
        return getSalt(SIZE64);
    }

    @Override
    public byte[] getSalt32() {
        return getSalt(SIZE32);
    }

    @Override
    public byte[] getSalt(int size) {
        final byte[] salt;
        if (size < SIZE32) {
            final String message = String
                    .format("Size < 32, using default of: %d", SIZE64);
            LOGGER.warn(message);
            salt = new byte[SIZE64];
        } else {
            salt = new byte[size];
        }
        RANDOM.nextBytes(salt);
        return salt;
    }

    @Override
    public byte[] hash(String password, byte[] salt) {
        if (!IsParams.isParams(password) || salt == null) {
            LOGGER.warn("NullPointerException, password" +
                    " or salt matches null");
            return new byte[0];
        }
        try {
            final byte[] all = getAll(password, salt);
            SHA3.DigestSHA3 md = new SHA3.Digest512();
            md.update(all);
            return md.digest();
        } catch (Exception e) {
            final String message = String.format(
                    "Caching unsupported encoding exception",
                    e.getMessage());
            LOGGER.error(message);
        }
        return new byte[0];
    }

    @Override
    public boolean isExpectedPassword(String password, byte[] salt,
                                      byte[] hash) {
        if (!IsParams.isParams(password) || salt == null ||
                hash == null) {
            LOGGER.warn("NullPointerException, password," +
                    "salt or hash matches null");
            return false;
        }
        try {
            final byte[] all = getAll(password, salt);
            SHA3.DigestSHA3 md = new SHA3.Digest512();
            md.update(all);
            final byte[] digest = md.digest();
            return Arrays.equals(digest, hash);
        } catch (Exception e) {
            final String message = String.format(
                    "Caching unsupported encoding exception",
                    e.getMessage());
            LOGGER.error(message);
        }
        return false;
    }

    private byte[] getAll(String password, byte[] salt) {
        final byte[] passwordBytes = password.getBytes(
                StandardCharsets.UTF_8);
        final byte[] all = ArrayUtils.addAll(passwordBytes, salt);
        return all;
    }

    @Override
    public String generateRandomPassword(int length) {
        if (length < 1) {
            return null;
        }
        final char[] buf = new char[length];
        for (int i = 0; i < buf.length; ++i) {
            buf[i] = symbols[RANDOM.nextInt(symbols.length)];
        }
        return shuffle(new String(buf));
    }

    private String shuffle(final String input) {
        final List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        final StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }
}
