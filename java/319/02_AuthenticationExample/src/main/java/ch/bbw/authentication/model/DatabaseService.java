package ch.bbw.authentication.model;

import java.util.HashMap;
import java.util.Map;

/**
 * DatabaseService
 *    Das ist ein einfacher Mock für die Datenbank,
 *    die user und hashes der Passwörter verwaltet.
 *    Der Mock simuliert dieses Verhalten.
 * @author Peter Rutschmann
 * @version 13.12.2022
 */
public class DatabaseService {
   private Map<String, String> userAndHash = new HashMap<>();

   public DatabaseService(){
      /* TODO hier können Sie weitere User mit Ihren Hash-codes eintragen.
       * Der Hash würde normalerweise beim Registrieren gespeichert.
       * Sie können den Hash via Online-Tool berechnen und hier eintragen.
       */

      //                              user   password-hash
      userAndHash.put("admin", "BA3253876AED6BC22D4A6FF53D8406C6AD864195ED144AB5C87621B6C233B548BAEAE6956DF346EC8C17F5EA10F35EE3CBC514797ED7DDD3145464E2A0BAB413");
      userAndHash.put("user", "BA3253876AED6BC22D4A6FF53D8406C6AD864195ED144AB5C87621B6C233B548BAEAE6956DF346EC8C17F5EA10F35EE3CBC514797ED7DDD3145464E2A0BAB413");
   }

   public String getHashByUser(String user){
      return userAndHash.get(user);
   }
}
