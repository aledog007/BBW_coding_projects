package ch.bbw.pr.tresorbackend.controller;

import ch.bbw.pr.tresorbackend.model.LoginResponse;
import ch.bbw.pr.tresorbackend.model.LoginUser;
import ch.bbw.pr.tresorbackend.model.User;
import ch.bbw.pr.tresorbackend.security.JwtTokenProvider;
import ch.bbw.pr.tresorbackend.service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * AuthController - Hier passiert die ganze Login-Magic.
 * Kümmert sich um Login, Refresh Tokens und das 2FA Zeugs
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncryptService passwordService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TotpService totpService;
    private final SecurityAuditService auditService;

    public AuthController(UserService userService,
                          PasswordEncryptService passwordService,
                          JwtTokenProvider jwtTokenProvider,
                          TotpService totpService,
                          SecurityAuditService auditService) {
        this.userService = userService;
        this.passwordService = passwordService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.totpService = totpService;
        this.auditService = auditService;
    }

    /**
     * Normaler Login.
     * Spuckt entweder direkt Tokens aus oder verlangt den 2FA-Code.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUser loginUser) {
        User user = userService.findByEmail(loginUser.getEmail());
        if (user == null) {
            auditService.logLoginFailure(loginUser.getEmail(), "User not found");
            return ResponseEntity.badRequest().body(
                new LoginResponse("E-Mail oder Passwort ist falsch", null, null, null, null, false, null));
        }

        // Schauen ob der Account wegen zu vielen Versuchen gesperrt ist
        if (user.getAccountLockedUntil() != null && user.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
            auditService.logLoginFailure(user.getEmail(), "Account locked");
            return ResponseEntity.status(HttpStatus.LOCKED).body(
                new LoginResponse("Account ist gesperrt. Bitte versuchen Sie es später.", null, null, null, null, false, null));
        }

        // Stimmt das Passwort?
        if (!passwordService.doPasswordMatch(loginUser.getPassword(), user.getPassword())) {
            // Falsches PW -> Fehler-Counter hochzählen
            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);
            if (attempts >= 5) {
                user.setAccountLockedUntil(LocalDateTime.now().plusMinutes(15));
                auditService.logAccountLocked(user.getEmail(), attempts);
            }
            userService.updateUser(user);
            auditService.logLoginFailure(user.getEmail(), "Wrong password (attempt " + attempts + ")");
            return ResponseEntity.badRequest().body(
                new LoginResponse("E-Mail oder Passwort ist falsch", null, null, null, null, false, null));
        }

        // Hat geklappt -> Counter wieder auf 0 setzen
        user.setFailedLoginAttempts(0);
        user.setAccountLockedUntil(null);
        userService.updateUser(user);

        // Hat der User 2FA aktiviert?
        if (user.isTwoFactorEnabled() && user.getTotpSecret() != null) {
            String tempToken = jwtTokenProvider.generateTempToken(user.getEmail());
            auditService.logLoginSuccess(user.getEmail(), "Password (2FA required)");
            return ResponseEntity.ok(new LoginResponse(
                "2FA-Code erforderlich", user.getId(), null, null, user.getRole(), true, tempToken));
        }

        // Kein 2FA nötig -> gib ihm direkt die Tokens
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getRole());
        auditService.logLoginSuccess(user.getEmail(), "Password");

        return ResponseEntity.ok(new LoginResponse(
            "Login erfolgreich", user.getId(), accessToken, refreshToken, user.getRole(), false, null));
    }

    /**
     * 2FA Code aus der Handy-App checken
     */
    @PostMapping("/2fa/verify")
    public ResponseEntity<LoginResponse> verify2FA(@RequestBody TwoFactorRequest request) {
        if (!jwtTokenProvider.validateToken(request.getTempToken())) {
            return ResponseEntity.badRequest().body(
                new LoginResponse("Ungültiger oder abgelaufener Token", null, null, null, null, false, null));
        }

        String email = jwtTokenProvider.getEmailFromToken(request.getTempToken());
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(
                new LoginResponse("User nicht gefunden", null, null, null, null, false, null));
        }

        if (!totpService.verifyCode(user.getTotpSecret(), request.getCode())) {
            auditService.logTwoFactorFailure(email);
            return ResponseEntity.badRequest().body(
                new LoginResponse("Ungültiger 2FA-Code", null, null, null, null, false, null));
        }

        auditService.logTwoFactorSuccess(email);
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(new LoginResponse(
            "Login erfolgreich (2FA verifiziert)", user.getId(), accessToken, refreshToken, user.getRole(), false, null));
    }

    /**
     * Frisches Access Token besorgen
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("message", "Ungültiger Refresh Token"));
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        String role = jwtTokenProvider.getRoleFromToken(refreshToken);
        String newAccessToken = jwtTokenProvider.generateAccessToken(email, role);

        auditService.logTokenRefresh(email);
        return ResponseEntity.ok(Map.of(
            "accessToken", newAccessToken,
            "message", "Token erneuert"
        ));
    }

    /**
     * 2FA Setup: Generiert das Secret und macht den QR-Code fertig
     */
    @PostMapping("/2fa/setup")
    public ResponseEntity<?> setup2FA(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User nicht gefunden"));
        }

        String secret = totpService.generateSecret();
        user.setTotpSecret(secret);
        userService.updateUser(user);

        try {
            String qrCodeUrl = totpService.getQrCodeUrl(secret, email);
            return ResponseEntity.ok(Map.of(
                "secret", secret,
                "qrCodeUrl", qrCodeUrl,
                "message", "Scannen Sie den QR-Code mit Ihrer Authenticator-App"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Fehler bei der QR-Code Generierung"));
        }
    }

    /**
     * 2FA endgültig scharfschalten nachdem der erste Code gepasst hat
     */
    @PostMapping("/2fa/enable")
    public ResponseEntity<?> enable2FA(Authentication authentication, @RequestBody Map<String, String> request) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        if (user == null || user.getTotpSecret() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "2FA Setup nicht gestartet"));
        }

        String code = request.get("code");
        if (!totpService.verifyCode(user.getTotpSecret(), code)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Ungültiger Code. Bitte versuchen Sie es erneut."));
        }

        user.setTwoFactorEnabled(true);
        userService.updateUser(user);
        auditService.logTwoFactorEnabled(email);

        return ResponseEntity.ok(Map.of("message", "2FA erfolgreich aktiviert!"));
    }

    /**
     * 2FA wieder abschalten
     */
    @PostMapping("/2fa/disable")
    public ResponseEntity<?> disable2FA(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User nicht gefunden"));
        }

        user.setTwoFactorEnabled(false);
        user.setTotpSecret(null);
        userService.updateUser(user);

        return ResponseEntity.ok(Map.of("message", "2FA deaktiviert"));
    }

    // Inner DTO for 2FA verify request
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TwoFactorRequest {
        private String tempToken;
        private String code;
    }
}
