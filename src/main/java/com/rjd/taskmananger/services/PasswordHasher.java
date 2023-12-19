package com.rjd.taskmananger.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class PasswordHasher implements PasswordEncoder {

    Key aesKey = null;

    public PasswordHasher(){
        readAESKey();
    }

    public void readAESKey(){
        try {
            // Read the key from the file
            Path keyPath = Paths.get("src/main/resources/aesKey.key");
            byte[] keyBytes = Files.readAllBytes(keyPath);

            // Convert the key bytes to a SecretKey
            SecretKey secretKey = convertBytesToSecretKey(keyBytes);

            aesKey = secretKey;
            System.out.println("AES Key read successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SecretKey convertBytesToSecretKey(byte[] keyBytes) throws Exception {
        return new SecretKeySpec(keyBytes, "AES");
    }


    @Override
    public String encode(CharSequence rawPassword) {
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] cipherText = cipher.doFinal(rawPassword.toString().getBytes());
            return Base64.getEncoder()
                    .encodeToString(cipherText);
        }catch(Exception e){

        }
        return null;
    }


    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
