package ch.bbw.pr.tresorbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ch.bbw.pr.tresorbackend.model.EmailAdress;
import ch.bbw.pr.tresorbackend.model.LoginResponse;
import ch.bbw.pr.tresorbackend.model.LoginUser;
import ch.bbw.pr.tresorbackend.model.RegisterUser;
import ch.bbw.pr.tresorbackend.model.ResetPasswordRequest;
import ch.bbw.pr.tresorbackend.model.User;
import ch.bbw.pr.tresorbackend.service.PasswordEncryptService;
import ch.bbw.pr.tresorbackend.service.PasswordResetService;
import ch.bbw.pr.tresorbackend.service.UserService;
import ch.bbw.pr.tresorbackend.util.EncryptUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;

/**
 * UserController
 * 
 * @author Peter Rutschmann
 */
@RestController
@RequestMapping("api/users")
public class UserController {

   private UserService userService;
   private PasswordEncryptService passwordService;
   private PasswordResetService passwordResetService;

   @Value("${recaptcha.secret.key:6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe}")
   private String recaptchaSecret;

   public UserController(UserService userService, PasswordEncryptService passwordService, PasswordResetService passwordResetService) {
      this.userService = userService;
      this.passwordService = passwordService;
      this.passwordResetService = passwordResetService;
   }
   private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // build create User REST API
   @PostMapping
   public ResponseEntity<String> createUser(@Valid @RequestBody RegisterUser registerUser,
         BindingResult bindingResult) {
      // captcha
      if (registerUser.getRecaptchaToken() == null || !verifyCaptcha(registerUser.getRecaptchaToken())) {
          return ResponseEntity.badRequest().body("{\"message\": [\"Captcha validation failed\"]}");
      }
      System.out.println("UserController.createUser: captcha passed.");

      // input validation
      if (bindingResult.hasErrors()) {
         List<String> errors = bindingResult.getFieldErrors().stream()
               .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
               .collect(Collectors.toList());
         System.out.println("UserController.createUser " + errors);

         JsonArray arr = new JsonArray();
         errors.forEach(arr::add);
         JsonObject obj = new JsonObject();
         obj.add("message", arr);
         String json = new Gson().toJson(obj);

         System.out.println("UserController.createUser, validation fails: " + json);
         return ResponseEntity.badRequest().body(json);
      }
      System.out.println("UserController.createUser: input validation passed");

      // password validation
      if (!registerUser.getPassword().equals(registerUser.getPasswordConfirmation())) {
          return ResponseEntity.badRequest().body("{\"message\": [\"Password and Password-Confirmation do not match.\"]}");
      }
      System.out.println("UserController.createUser, password validation passed");

      // transform registerUser to user
      User user = new User();
      user.setFirstName(registerUser.getFirstName());
      user.setLastName(registerUser.getLastName());
      user.setEmail(registerUser.getEmail());
      user.setPassword(passwordService.hashPassword(registerUser.getPassword()));
      user.setSalt(EncryptUtil.generateSalt());
      user.setRole("ROLE_USER");
      user.setTwoFactorEnabled(false);
      user.setFailedLoginAttempts(0);

      User savedUser = userService.createUser(user);
      JsonObject obj = new JsonObject();
      if (savedUser != null) {
         System.out.println("UserController.createUser, user saved in db");
         obj.addProperty("answer", "User saved");
      } else {
         System.out.println("UserController.createUser, user not saved in db");
         obj.addProperty("answer", "User not saved");
      }
      String json = new Gson().toJson(obj);
      System.out.println("UserController.createUser " + json);
      return ResponseEntity.accepted().body(json);
   }

   // build get user by id REST API
   // http://localhost:8080/api/users/1
   @GetMapping("{id}")
   public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
      User user = userService.getUserById(userId);
      if (user == null)
         return ResponseEntity.notFound().build();
      return new ResponseEntity<>(user, HttpStatus.OK);
   }

   // Build Get All Users REST API
   // http://localhost:8080/api/users
   @org.springframework.security.access.prepost.PreAuthorize("hasAuthority('ROLE_ADMIN')")
   @GetMapping
   public ResponseEntity<List<User>> getAllUsers() {
      List<User> users = userService.getAllUsers();
      if (users.isEmpty())
         return ResponseEntity.notFound().build();
      return new ResponseEntity<>(users, HttpStatus.OK);
   }

   // Build Update User REST API
   // http://localhost:8080/api/users/1
   @PutMapping("{id}")
   public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
         @RequestBody User user) {
      user.setId(userId);
      User updatedUser = userService.updateUser(user);
      if (updatedUser == null)
         return ResponseEntity.notFound().build();
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
   }

   // Build Delete User REST API
   @org.springframework.security.access.prepost.PreAuthorize("hasAuthority('ROLE_ADMIN')")
   @DeleteMapping("{id}")
   public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
      if (userService.deleteUser(userId))
         return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
      return ResponseEntity.notFound().build();
   }

   // get user id by email
   @PostMapping("/byemail")
   public ResponseEntity<String> getUserIdByEmail(@RequestBody EmailAdress email, BindingResult bindingResult) {
      System.out.println("UserController.getUserIdByEmail: " + email);
      // input validation
      if (bindingResult.hasErrors()) {
         List<String> errors = bindingResult.getFieldErrors().stream()
               .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
               .collect(Collectors.toList());
         System.out.println("UserController.createUser " + errors);

         JsonArray arr = new JsonArray();
         errors.forEach(arr::add);
         JsonObject obj = new JsonObject();
         obj.add("message", arr);
         String json = new Gson().toJson(obj);

         System.out.println("UserController.createUser, validation fails: " + json);
         return ResponseEntity.badRequest().body(json);
      }

      System.out.println("UserController.getUserIdByEmail: input validation passed");

      User user = userService.findByEmail(email.getEmail());
      if (user == null) {
         System.out.println("UserController.getUserIdByEmail, no user found with email: " + email);
         JsonObject obj = new JsonObject();
         obj.addProperty("message", "No user found with this email");
         String json = new Gson().toJson(obj);

         System.out.println("UserController.getUserIdByEmail, fails: " + json);
         return ResponseEntity.badRequest().body(json);
      }
      System.out.println("UserController.getUserIdByEmail, user find by email");
      JsonObject obj = new JsonObject();
      obj.addProperty("answer", user.getId());
      String json = new Gson().toJson(obj);
      System.out.println("UserController.getUserIdByEmail " + json);
      return ResponseEntity.accepted().body(json);
   }

   // simple login with no websecurity, just name and password
   // DEPRECATED: Bitte AuthController für Login nutzen
   @PostMapping("/login")
   public ResponseEntity<LoginResponse> doLoginUser(@RequestBody LoginUser loginUser, BindingResult bindingResult) {
      System.out.println("UserController.doLoginUser: " + loginUser);

      if (bindingResult.hasErrors()) {
         String errorMessage = bindingResult.getFieldErrors().stream()
               .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
               .collect(Collectors.joining("; "));
         return ResponseEntity.badRequest().body(new LoginResponse(errorMessage, null));
      }

      User user = userService.findByEmail(loginUser.getEmail());
      if (user == null) {
         System.out.println("UserController.doLoginUser: user not found");
         // Security Best Practice: Generische Meldung verhindert User-Enumeration.
         return ResponseEntity.badRequest().body(new LoginResponse("E-Mail oder Passwort ist falsch", null));
      }

      // Passwort-Verifizierung:
      // Wir vergleichen das Klartext-Passwort vom Login-Formular mit dem Hash aus der
      // DB.
      // Die Methode 'doPasswordMatch' kümmert sich intern um die Pepper-Logik.
      if (!passwordService.doPasswordMatch(loginUser.getPassword(), user.getPassword())) {
         System.out.println("UserController.doLoginUser: password verification failed");
         return ResponseEntity.badRequest().body(new LoginResponse("E-Mail oder Passwort ist falsch", null));
      }

      System.out.println("UserController.doLoginUser: login successful");
      return ResponseEntity.ok(new LoginResponse("Login successful", user.getId()));
   }

   @PostMapping("/forgot-password")
   public ResponseEntity<?> forgotPassword(@RequestBody EmailAdress emailAdress) {
       User user = userService.findByEmail(emailAdress.getEmail());
       // Always return OK to prevent email enumeration
       if (user != null) {
           passwordResetService.createPasswordResetTokenForUser(user);
       }
       return ResponseEntity.ok("{\"message\": \"If the email exists, a reset link has been sent.\"}");
   }

   @PostMapping("/reset-password")
   public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request, BindingResult bindingResult) {
       if (bindingResult.hasErrors()) {
           List<String> errors = bindingResult.getFieldErrors().stream()
                   .map(fieldError -> fieldError.getDefaultMessage())
                   .collect(Collectors.toList());
           return ResponseEntity.badRequest().body(errors);
       }

       if (!request.getPassword().equals(request.getPasswordConfirmation())) {
           return ResponseEntity.badRequest().body(List.of("Passwords do not match."));
       }

       if (!passwordResetService.validatePasswordResetToken(request.getToken())) {
           return ResponseEntity.badRequest().body(List.of("Invalid or expired token."));
       }

       User user = passwordResetService.getUserByPasswordResetToken(request.getToken());
       if (user == null) {
           return ResponseEntity.badRequest().body(List.of("Invalid token."));
       }

       passwordResetService.changeUserPassword(user, request.getPassword());
       return ResponseEntity.ok("{\"message\": \"Password successfully reset.\"}");
   }

   private boolean verifyCaptcha(String token) {
       try {
           String url = "https://www.google.com/recaptcha/api/siteverify";
           String params = "secret=" + recaptchaSecret + "&response=" + token;

           HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
           conn.setRequestMethod("POST");
           conn.setDoOutput(true);
           conn.getOutputStream().write(params.getBytes(StandardCharsets.UTF_8));

           Scanner scanner = new Scanner(conn.getInputStream());
           String response = scanner.useDelimiter("\\A").next();
           scanner.close();

           return response.contains("\"success\": true");
       } catch (Exception e) {
           return false;
       }
   }
}
