
package com.codequest.util;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final int ITERATIONS = 210000;
    private static final int KEY_LENGTH = 256;

    // Generate a secure password hash
    public static String hashPassword(String password)
            throws Exception {

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
        );

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance(
                        "PBKDF2WithHmacSHA256");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        return ITERATIONS + ":"
                + Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    // Verify entered password with stored password hash
    public static boolean verifyPassword(
            String password, String storedHash) throws Exception {

        String[] parts = storedHash.split(":");

        if (parts.length != 3) {
            return false;
        }

        int iterations = Integer.parseInt(parts[0]);

        if (iterations < 10000 || iterations > 1000000) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] originalHash = Base64.getDecoder().decode(parts[2]);

        if (salt.length != 16 || originalHash.length != 32) {
            return false;
        }

        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                iterations,
                originalHash.length * 8
        );

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance(
                        "PBKDF2WithHmacSHA256");

        byte[] newHash = factory.generateSecret(spec).getEncoded();

        return MessageDigest.isEqual(originalHash, newHash);
    }
}
