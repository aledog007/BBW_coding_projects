package ch.bbw.pr.tresorbackend.controller;

import ch.bbw.pr.tresorbackend.model.Secret;
import ch.bbw.pr.tresorbackend.model.NewSecret;
import ch.bbw.pr.tresorbackend.model.EncryptCredentials;
import ch.bbw.pr.tresorbackend.model.User;
import ch.bbw.pr.tresorbackend.service.SecretService;
import ch.bbw.pr.tresorbackend.service.UserService;
import ch.bbw.pr.tresorbackend.util.EncryptUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SecretController
 * @author Peter Rutschmann
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/secrets")
public class SecretController {

   private SecretService secretService;
   private UserService userService;

   // create secret REST API
   @PostMapping
   public ResponseEntity<String> createSecret2(@Valid @RequestBody NewSecret newSecret, BindingResult bindingResult) {
      // input validation
      if (bindingResult.hasErrors()) {
         List<String> errors = bindingResult.getFieldErrors().stream()
               .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
               .collect(Collectors.toList());
         
         JsonObject obj = new JsonObject();
         JsonArray arr = new JsonArray();
         errors.forEach(arr::add);
         obj.add("message", arr);
         
         return ResponseEntity.badRequest().body(new Gson().toJson(obj));
      }

      User user = userService.findByEmail(newSecret.getEmail());
      if (user == null || user.getSalt() == null) {
         return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("{\"message\": \"Benutzer oder Salt nicht gefunden. Bitte neu registrieren.\"}");
      }

      // Verschlüsselung mit individuellem Salt vorbereiten
      EncryptUtil encryptUtil = new EncryptUtil(newSecret.getEncryptPassword(), user.getSalt());
      
      Secret secret = new Secret(
            null,
            user.getId(),
            encryptUtil.encrypt(newSecret.getContent().toString())
      );
      
      secretService.createSecret(secret);
      
      JsonObject obj = new JsonObject();
      obj.addProperty("answer", "Secret erfolgreich verschlüsselt gespeichert");
      return ResponseEntity.accepted().body(new Gson().toJson(obj));
   }

   // Build Get Secrets by userId REST API
   @PostMapping("/byuserid")
   public ResponseEntity<?> getSecretsByUserId(@RequestBody EncryptCredentials credentials) {
      User user = userService.getUserById(credentials.getUserId());
      if (user == null || user.getSalt() == null) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Benutzer oder Salt nicht gefunden\"}");
      }

      List<Secret> secrets = secretService.getSecretsByUserId(credentials.getUserId());
      // Auch wenn die Liste leer ist, geben wir ein leeres Array zurück (valides JSON)
      EncryptUtil encryptUtil = new EncryptUtil(credentials.getEncryptPassword(), user.getSalt());

      for(Secret secret: secrets) {
         try {
            secret.setContent(encryptUtil.decrypt(secret.getContent()));
         } catch (Exception e) {
            // MIGRATION-LOGIK: Falls das Secret noch im alten JSON-Format ist (z.B. Testdaten), zeigen wir es an
            if (secret.getContent().trim().startsWith("{")) {
               System.out.println("Altes (unverschlüsseltes) Secret erkannt.");
            } else {
               secret.setContent("{\"error\": \"Entschlüsselung fehlgeschlagen. Falsches Passwort?\"}");
            }
         }
      }

      return ResponseEntity.ok(secrets);
   }

   // Build Get Secrets by email REST API
   @PostMapping("/byemail")
   public ResponseEntity<?> getSecretsByEmail(@RequestBody EncryptCredentials credentials) {
      User user = userService.findByEmail(credentials.getEmail());
      if (user == null || user.getSalt() == null) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Benutzer oder Salt nicht gefunden\"}");
      }

      List<Secret> secrets = secretService.getSecretsByUserId(user.getId());
      EncryptUtil encryptUtil = new EncryptUtil(credentials.getEncryptPassword(), user.getSalt());

      for(Secret secret: secrets) {
         try {
            secret.setContent(encryptUtil.decrypt(secret.getContent()));
         } catch (Exception e) {
            if (secret.getContent().trim().startsWith("{")) {
               // OK, altes Format
            } else {
               secret.setContent("{\"error\": \"Entschlüsselung nicht möglich.\"}");
            }
         }
      }

      return ResponseEntity.ok(secrets);
   }

   // Alle Secrets abrufen (für Admin/System Übersicht)
   @org.springframework.security.access.prepost.PreAuthorize("hasAuthority('ROLE_ADMIN')")
   @GetMapping
   public ResponseEntity<List<Secret>> getAllSecrets() {
      List<Secret> secrets = secretService.getAllSecrets();
      return new ResponseEntity<>(secrets, HttpStatus.OK);
   }

   // Build Update Secret REST API
   @PutMapping("{id}")
   public ResponseEntity<String> updateSecret(
         @PathVariable("id") Long secretId,
         @Valid @RequestBody NewSecret newSecret,
         BindingResult bindingResult) {
      
      if (bindingResult.hasErrors()) {
         return ResponseEntity.badRequest().body("Validierung fehlgeschlagen");
      }

      Secret dbSecrete = secretService.getSecretById(secretId);
      if(dbSecrete == null){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secret nicht gefunden");
      }
      
      User user = userService.findByEmail(newSecret.getEmail());
      if (user == null || user.getSalt() == null) return ResponseEntity.notFound().build();

      if(!dbSecrete.getUserId().equals(user.getId())){
         return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Keine Berechtigung");
      }
      
      EncryptUtil encryptUtil = new EncryptUtil(newSecret.getEncryptPassword(), user.getSalt());

      Secret secret = new Secret(
            secretId,
            user.getId(),
            encryptUtil.encrypt(newSecret.getContent().toString())
      );
      secretService.updateSecret(secret);
      
      return ResponseEntity.accepted().body("Secret erfolgreich aktualisiert");
   }

   // Build Delete Secret REST API
   @DeleteMapping("{id}")
   public ResponseEntity<String> deleteSecret(
         @PathVariable("id") Long secretId,
         @RequestParam String email) {
      
      Secret secret = secretService.getSecretById(secretId);
      if (secret == null) return ResponseEntity.notFound().build();
      
      User user = userService.findByEmail(email);
      // Prüfung, ob der User wirklich der Besitzer ist
      if (user == null || !secret.getUserId().equals(user.getId())) {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Keine Berechtigung zum Löschen.");
      }
      
      secretService.deleteSecret(secretId);
      return new ResponseEntity<>("Secret erfolgreich gelöscht!", HttpStatus.OK);
   }
}
