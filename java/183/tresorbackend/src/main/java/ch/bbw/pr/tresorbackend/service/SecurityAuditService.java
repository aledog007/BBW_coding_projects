package ch.bbw.pr.tresorbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SecurityAuditService - Loggt alle sicherheitsrelevanten Events.
 * Wow-Effekt: Strukturiertes Security Audit Logging.
 */
@Service
public class SecurityAuditService {

    private static final Logger auditLog = LoggerFactory.getLogger("SECURITY_AUDIT");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void logLoginSuccess(String email, String method) {
        audit("LOGIN_SUCCESS", email, "Authentication method: " + method);
    }

    public void logLoginFailure(String email, String reason) {
        audit("LOGIN_FAILURE", email, reason);
    }

    public void logAccountLocked(String email, int attempts) {
        audit("ACCOUNT_LOCKED", email, "Locked after " + attempts + " failed attempts");
    }

    public void logTwoFactorSuccess(String email) {
        audit("2FA_SUCCESS", email, "TOTP verification passed");
    }

    public void logTwoFactorFailure(String email) {
        audit("2FA_FAILURE", email, "TOTP verification failed");
    }

    public void logTwoFactorEnabled(String email) {
        audit("2FA_ENABLED", email, "Two-factor authentication activated");
    }

    public void logOAuthLogin(String email, String provider) {
        audit("OAUTH_LOGIN", email, "Provider: " + provider);
    }

    public void logPasswordReset(String email) {
        audit("PASSWORD_RESET", email, "Password successfully reset");
    }

    public void logTokenRefresh(String email) {
        audit("TOKEN_REFRESH", email, "Access token refreshed");
    }

    private void audit(String event, String email, String details) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        auditLog.info("[{}] {} | User: {} | {}", timestamp, event, email, details);
    }
}
