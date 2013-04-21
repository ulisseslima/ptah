package com.dvlcube.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Wonka
 */
public final class PasswordService {

    private static PasswordService instance;

    private PasswordService() {
    }

    public synchronized String encrypt(String plaintext) throws SystemUnavailableException {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new SystemUnavailableException(e.getMessage());
        }
        try {
            messageDigest.update(plaintext.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new SystemUnavailableException(e.getMessage());
        }
        byte raw[] = messageDigest.digest();
        String hash = (new BASE64Encoder()).encode(raw);
        return hash;
    }

    public static synchronized PasswordService getInstance() {
        if (instance == null) {
            return new PasswordService();
        } else {
            return instance;
        }
    }
}
