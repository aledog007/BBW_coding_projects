package ch.bbw.pr.tresorbackend.service;

import ch.bbw.pr.tresorbackend.model.User;
import ch.bbw.pr.tresorbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncryptService passwordService;
    private final ch.bbw.pr.tresorbackend.repository.SecretRepository secretRepository;

    public void createPasswordResetTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        // Simulate sending email by printing to console
        String resetPasswordBaseUrl = "http://localhost:3000/reset-password/";
        String tokenLink = resetPasswordBaseUrl + token;
        System.out.println("PasswordResetService.sendEmail: ");
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: Tresor application. Reset your password");
        System.out.println("Please press link to reset your password:\n"
                + tokenLink
                + "\n\nRemark: Link will expire in about one hour");
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getResetTokenExpiry().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    public User getUserByPasswordResetToken(String token) {
        return userRepository.findByResetToken(token).orElse(null);
    }

    @org.springframework.transaction.annotation.Transactional
    public void changeUserPassword(User user, String newPassword) {
        // Altes Passwort geht verloren -> alte Secrets können nicht mehr entschlüsselt werden. 
        // Konsequenz: Wir löschen sie, um Datenmüll zu verhindern.
        secretRepository.deleteByUserId(user.getId());

        user.setPassword(passwordService.hashPassword(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}
