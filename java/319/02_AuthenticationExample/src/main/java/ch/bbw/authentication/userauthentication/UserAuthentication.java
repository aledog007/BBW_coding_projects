package ch.bbw.authentication.userauthentication;

import java.nio.charset.StandardCharsets;

import java.nio.charset.StandardCharsets;
import ch.bbw.authentication.model.DatabaseService;

/**
 * UserAuthentication
 * Überprüft ob passwort des user zum Hash in der DB passt
 * 
 * @author Peter Rutschmann
 * @version 13.12.2022
 */
public class UserAuthentication {

   static DatabaseService service = new DatabaseService();

   public static boolean validatePassword(String user, String password) {
      String hashFromDB = service.getHashByUser(user);

      /*
       * TODO
       * hier aus dem passwort den Hash berechnen.
       * Dann mit dem Hash aus der DB vergleichen
       * hashFromDB.equals(..
       * Ist der Hash gleich, dann ist die Authentication ok
       * sonst nicht
       */

      return false;
   }
}
