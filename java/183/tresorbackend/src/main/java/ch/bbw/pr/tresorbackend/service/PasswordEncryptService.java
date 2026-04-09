package ch.bbw.pr.tresorbackend.service;

import org.springframework.stereotype.Service;

/**
 * PasswordEncryptService
 * used to hash password and verify match
 * 
 * @author Peter Rutschmann
 */
@Service
public class PasswordEncryptService {
   @org.springframework.beans.factory.annotation.Value("${security.pepper}")
   private String pepper;

   // BCrypt ist ein Key-Stretching-Algorithmus (absichtlich rechenintensiv).
   // Ein Salt wird von BCrypt automatisch generiert und im Hash gespeichert.
   private final org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder 
         = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();


   /**
    * Erzeugt einen sicheren Hash eines Passworts.
    * 
    * SICHERHEITSKONZEPT:
    * 1. Pepper: Wir hängen den Pepper an das Passwort. Falls die DB gestohlen wird,
    *    kann ein Angreifer ohne den Pepper (vom Server) die Hashes nicht knacken.
    * 2. Salting: BCrypt nutzt für jeden User ein individuelles Salt (automatisch).
    *    Dies verhindert Rainbow-Table-Angriffe.
    * 3. Hashing: BCrypt ist resistent gegen massiv-paralleles Cracking (GPUs).
    */
   public String hashPassword(String password) {
      if (password == null) {
         return null;
      }
      String pepperedPassword = password + pepper;
      return passwordEncoder.encode(pepperedPassword);
   }

   /**
    * Vergleicht ein Klartext-Passwort mit einem gespeicherten Hash.
    * 
    * ABLAUF:
    * 1. Der Pepper wird erneut an das eingegebene Passwort gehängt.
    * 2. BCrypt extrahiert das Salt aus dem 'encodedHash'.
    * 3. BCrypt hasht das neue Wort mit diesem Salt und vergleicht das Resultat.
    */
   public boolean doPasswordMatch(String rawPassword, String encodedHash) {
      if (rawPassword == null || encodedHash == null) {
         return false;
      }
      // Der Pepper muss exakt derselbe wie bei der Registrierung sein
      String pepperedPassword = rawPassword + pepper;

      // Die Methode 'matches' kümmert sich um den sicheren Vergleich
      return passwordEncoder.matches(pepperedPassword, encodedHash);
   }

}