package com.bank.security;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Base64;

public class TokenUtil {

    private static final String KEYSTORE_PATH = "/serverkeystore.jks"; // Liegt im resources-Ordner
    private static final String KEYSTORE_PASSWORD = "geheim123";
    private static final String KEY_ALIAS = "serverkey";
    private static final String KEY_PASSWORD = "geheim123";

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    static {
        try {
            // Keystore laden
            KeyStore ks = KeyStore.getInstance("JKS");
            InputStream is = TokenUtil.class.getResourceAsStream(KEYSTORE_PATH);
            if (is == null) {
                throw new RuntimeException("Keystore-Datei nicht gefunden: " + KEYSTORE_PATH);
            }
            ks.load(is, KEYSTORE_PASSWORD.toCharArray());

            // Privater Schlüssel
            privateKey = (PrivateKey) ks.getKey(KEY_ALIAS, KEY_PASSWORD.toCharArray());

            // Öffentliches Zertifikat
            Certificate cert = ks.getCertificate(KEY_ALIAS);
            publicKey = cert.getPublicKey();

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden des Keystores", e);
        }
    }

    public static String generateToken(String iban) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(iban.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signature.sign();
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Token-Generieren", e);
        }
    }

    public static boolean validateToken(String iban, String token) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(iban.getBytes(StandardCharsets.UTF_8));
            byte[] signed = Base64.getDecoder().decode(token);
            return signature.verify(signed);
        } catch (Exception e) {
            return false;
        }
    }
}