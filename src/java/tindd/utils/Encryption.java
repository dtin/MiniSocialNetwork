/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Tin
 */
public class Encryption {
    private static byte[] hashToSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    private static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length() < 32) {
            hexString.insert(0, "0");
        }
        
        return hexString.toString();
    }
    
    public static String getEncryptedSHA(String input) throws NoSuchAlgorithmException {
        return toHexString(hashToSHA(input));
    }
}
