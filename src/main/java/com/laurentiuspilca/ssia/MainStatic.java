package com.laurentiuspilca.ssia;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class MainStatic {

    public static void main(String[] args) {
        doHashingPassword();
    }

    private static void doKeyGenerator() {
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

    private static void doHashingPassword() {
        final String password = "11111";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();

        System.out.println(bCryptPasswordEncoder.encode(password));
        System.out.println(sCryptPasswordEncoder.encode(password));

        System.out.println(bCryptPasswordEncoder.matches(password, "$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG"));
        System.out.println(sCryptPasswordEncoder.matches(password,"$e0801$VeCUsPFiRgCMRuD4EQZub060MB8ebCI/BSeSyXjr1cwYImtxSb7UhE9lJ9ofFRog+z1hT7zhhbvfBrpg3KPIPQ==$IaS7N89EPepocIMN+BgAgV0OSBCyDKRFV2KQPLSMTL4="));
    }

}
