package ch.bbw.pr.tresorbackend.security;

import ch.bbw.pr.tresorbackend.model.User;
import ch.bbw.pr.tresorbackend.service.SecurityAuditService;
import ch.bbw.pr.tresorbackend.service.UserService;
import ch.bbw.pr.tresorbackend.util.EncryptUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * OAuth2SuccessHandler - Verarbeitet erfolgreiche Google OAuth2 Logins.
 * Erstellt den User in der DB falls nötig und generiert einen JWT.
 */
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final SecurityAuditService auditService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public OAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider,
                                UserService userService,
                                SecurityAuditService auditService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.auditService = auditService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");

        // User in DB suchen oder neu erstellen
        User user = userService.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setFirstName(firstName != null ? firstName : "OAuth");
            user.setLastName(lastName != null ? lastName : "User");
            user.setPassword("OAUTH2_USER"); // Kein Passwort bei OAuth
            user.setSalt(EncryptUtil.generateSalt());
            user.setRole("ROLE_USER");
            user.setTwoFactorEnabled(false);
            user.setFailedLoginAttempts(0);
            user = userService.createUser(user);
        }

        // JWT generieren
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getRole());
        auditService.logOAuthLogin(email, "Google");

        // Redirect zum Frontend mit Tokens als Query-Parameter
        String redirectUrl = String.format("%s/oauth2/callback?accessToken=%s&refreshToken=%s&role=%s&email=%s",
            frontendUrl,
            URLEncoder.encode(accessToken, StandardCharsets.UTF_8),
            URLEncoder.encode(refreshToken, StandardCharsets.UTF_8),
            URLEncoder.encode(user.getRole(), StandardCharsets.UTF_8),
            URLEncoder.encode(email, StandardCharsets.UTF_8)
        );
        response.sendRedirect(redirectUrl);
    }
}
