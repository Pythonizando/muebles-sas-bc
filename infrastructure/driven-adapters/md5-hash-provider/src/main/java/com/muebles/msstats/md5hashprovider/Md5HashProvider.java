package com.muebles.msstats.md5hashprovider;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.muebles.msstats.model.stats.gateways.HashProvider;

@Component
public class Md5HashProvider implements HashProvider {

    @Override
    public String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : messageDigest) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular MD5 hash", e);
        }
    }
}   
