package ch.bbw.pr.tresorbackend.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * EncryptUtil
 * Implementiert die symmetrische Verschlüsselung mittels AES und PBKDF2.
 * 
 * SICHERHEITSKONZEPT:
 * 1. Key Derivation: Der AES-Schlüssel wird aus dem Benutzerpasswort und einem
 * Salt abgeleitet.
 * 2. Algorithmus: AES/CBC/PKCS5Padding (CBC-Modus mit Initialisierungsvektor).
 * 3. IV: Ein zufälliger IV pro Verschlüsselung sorgt für unterschiedliche
 * Chiffretexte bei gleichen Daten.
 * 
 * @author Peter Rutschmann
 */
public class EncryptUtil {

    private final SecretKey secretKey;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 256;
    private static final int ITERATIONS = 65536;

    /**
     * Erstellt ein EncryptUtil-Objekt mit einem individuellen Schlüssel.
     * 
     * @param password   Das Passwort des Benutzers (als Grundlage für den Key).
     * @param saltBase64 Der individuelle Salt des Benutzers (Base64-kodiert).
     */
    public EncryptUtil(String password, String saltBase64) {
        try {
            this.secretKey = deriveKey(password, saltBase64);
        } catch (Exception e) {
            throw new RuntimeException("Fehler bei der Schlüsselableitung", e);
        }
    }

    /**
     * Leitet einen starken AES-Key aus dem Passwort und Salt ab (PBKDF2).
     */
    private SecretKey deriveKey(String password, String saltBase64) throws Exception {
        byte[] salt = Base64.getDecoder().decode(saltBase64);
        // PBKDF2 Konfiguration
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * Verschlüsselt die Daten und fügt den IV am Anfang hinzu.
     */
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Zufälligen IV generieren
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // IV + Verschlüsselte Daten kombinieren
            byte[] combined = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Fehler bei der Verschlüsselung", e);
        }
    }

    /**
     * Entschlüsselt die Daten (extrahiert zuerst den IV).
     */
    public String decrypt(String encryptedBase64) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedBase64);

            // IV extrahieren (die ersten 16 Bytes)
            byte[] iv = new byte[16];
            System.arraycopy(combined, 0, iv, 0, 16);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Eigentliche Daten extrahieren
            int encryptedLength = combined.length - 16;
            byte[] encryptedData = new byte[encryptedLength];
            System.arraycopy(combined, 16, encryptedData, 0, encryptedLength);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decryptedData = cipher.doFinal(encryptedData);

            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Fehler bei der Entschlüsselung. Falsches Passwort oder korrupte Daten?", e);
        }
    }

    /**
     * Hilfsmethode: Generiert einen neuen zufälligen Salt für neue Benutzer.
     */
    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
