package ch.bbw.pr.tresorbackend.service;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.util.Utils;
import org.springframework.stereotype.Service;

/**
 * TotpService - TOTP (Time-based One-Time Password) für 2FA.
 * Kompatibel mit Google Authenticator, Authy, Microsoft Authenticator etc.
 */
@Service
public class TotpService {

    private final SecretGenerator secretGenerator = new DefaultSecretGenerator();

    /**
     * Generiert ein neues TOTP-Secret für einen User.
     */
    public String generateSecret() {
        return secretGenerator.generate();
    }

    /**
     * Erstellt die otpauth:// URL für den QR-Code.
     * Diese URL wird von Authenticator-Apps gescannt.
     */
    public String getQrCodeUrl(String secret, String email) throws QrGenerationException {
        QrData data = new QrData.Builder()
            .label(email)
            .secret(secret)
            .issuer("TresorApp")
            .algorithm(HashingAlgorithm.SHA1)
            .digits(6)
            .period(30)
            .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(data);
        String mimeType = generator.getImageMimeType();

        return Utils.getDataUriForImage(imageData, mimeType);
    }

    /**
     * Verifiziert einen TOTP-Code gegen das gespeicherte Secret.
     * Erlaubt ein Zeitfenster von ±1 Periode (30 Sekunden).
     */
    public boolean verifyCode(String secret, String code) {
        CodeVerifier verifier = new DefaultCodeVerifier(
            new DefaultCodeGenerator(), new SystemTimeProvider()
        );
        // DefaultCodeVerifier erlaubt standardmässig ±1 Zeitperiode
        return verifier.isValidCode(secret, code);
    }
}
