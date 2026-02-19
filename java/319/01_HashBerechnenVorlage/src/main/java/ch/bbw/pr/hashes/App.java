package ch.bbw.pr.hashes;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Application
 * @author Peter Rutschmann
 * @date 13.12.2022
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Berechne einige Hashes" );

        //TODO hier Passwort eintragen
        String password = "GH5&7Uh6*4g";
        //TODO hier hash von Passwort mit Hilfe von Hashing.sha256 berechnen
        String sha256hex = Hashing.sha256()
                        .hashString(password, StandardCharsets.UTF_8)
                                .toString();

        System.out.println("Passwort: " + password);
        System.out.println("HashFunction: sha256");
        System.out.println("Hash: " + sha256hex);
        System.out.println();

        //TODO hier hash von Passwort mit Hilfe von Hashing.sha512 berechnen
        String sha512hex = Hashing.sha512()
                        .hashString(password, StandardCharsets.UTF_8)
                                .toString();

        System.out.println("Passwort: " + password);
        System.out.println("HashFunction: sha512");
        System.out.println("Hash: " + sha512hex);
        System.out.println();
        //TODO Zusatzaufgabe ausprobieren, welche anderen Hashfunktionen Hashing unterstützt

        String sha384hex = Hashing.sha384()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        System.out.println("Passwort: " + password);
        System.out.println("HashFunction: sha384");
        System.out.println("Hash: " + sha384hex);
        System.out.println();
    }
}
