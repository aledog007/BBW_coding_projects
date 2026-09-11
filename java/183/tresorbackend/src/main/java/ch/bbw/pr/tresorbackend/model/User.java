package ch.bbw.pr.tresorbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * User
 * @author Peter Rutschmann
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, name="first_name")
   private String firstName;

   @Column(nullable = false, name="last_name")
   private String lastName;

   @Column(nullable = false, unique = true)
   private String email;

   @JsonIgnore
   @Column(nullable = false)
   private String password;

   @Column
   private String salt;

   @Column(name = "reset_token")
   private String resetToken;

   @Column(name = "reset_token_expiry")
   private LocalDateTime resetTokenExpiry;

   @Column(nullable = false)
   private String role = "ROLE_USER";

   @JsonIgnore
   @Column(name = "totp_secret")
   private String totpSecret;

   @Column(name = "two_factor_enabled", nullable = false)
   private boolean twoFactorEnabled = false;

   @Column(name = "failed_login_attempts", nullable = false)
   private int failedLoginAttempts = 0;

   @Column(name = "account_locked_until")
   private LocalDateTime accountLockedUntil;
}