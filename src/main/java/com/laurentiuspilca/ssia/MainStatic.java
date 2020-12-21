package com.laurentiuspilca.ssia;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class MainStatic {

    public static void main(String[] args) {
        String salt = KeyGenerators.string().generateKey();
        String password = "secret";
        String valueToEncrypt = "HELLO";

        TextEncryptor e = Encryptors.queryableText(password, "0811328e9a2750d0");
//        TextEncryptor e = Encryptors.text(password, salt);
        String encrypted = e.encrypt(valueToEncrypt);
        String decrypted = e.decrypt(encrypted);

        System.out.println(salt);
        System.out.println(encrypted);
        System.out.println(decrypted);

        System.out.println(e.decrypt("0d258fdef8033b9544ba8a7540b9a9da"));


    }

}
