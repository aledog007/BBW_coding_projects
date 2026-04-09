# Erläuterung: Sicheres Passwort-Hashing (BCrypt & Pepper)

Hier findest du eine Zusammenfassung der Änderungen, damit du sie in deiner Präsentation direkt im Code zeigen kannst.

---

## 1. Der server-seitige Pepper
**Datei:** `application.properties`

Wir haben einen geheimen Schlüssel (Pepper) hinzugefügt, der außerhalb des Codes in der Konfiguration liegt.

```properties
# Sicherheitseinstellungen (Hashing)
security.pepper=MeinSuperGeheimerTresorPasswortPepperMitVielEntropie123!
```

**Warum?** 
*   Sollte die Datenbank gestohlen werden, sind die Hashes ohne diesen Pepper wertlos. Er bietet eine zweite Schutzschicht (Zwei-Faktor-Sicherheit für die Daten).

---

## 2. Die Hashing-Logik
**Datei:** `PasswordEncryptService.java`

Hier findet die eigentliche „Magie“ statt. Wir nutzen **BCrypt**, den aktuellen Industriestandard.

```java
// Passwort mit Pepper kombinieren
String pepperedPassword = password + pepper;

// BCrypt generiert automatisch ein Salt und hasht alles zusammen
return passwordEncoder.encode(pepperedPassword);
```

**Warum?**
*   **Salting:** BCrypt erzeugt für jeden User ein individuelles Salt. Selbe Passwörter (z.B. „123456“) ergeben so immer völlig unterschiedliche Hashes.
*   **Key Stretching:** BCrypt ist rechenintensiv. Das verhindert, dass Angreifer mit Hochleistungs-GPUs Millionen von Passwörtern pro Sekunde testen können.

---

## 3. Die Login-Verifizierung
**Datei:** `UserController.java`

Im Login-Endpunkt gleichen wir die Eingabe des Benutzers sicher ab.

```java
// Die matches-Methode kümmert sich um den sicheren Vergleich
if (!passwordService.doPasswordMatch(loginUser.getPassword(), user.getPassword())) {
    // Generische Fehlermeldung für maximale Sicherheit
    return ResponseEntity.badRequest().body(new LoginResponse("E-Mail oder Passwort ist falsch", null));
}
```

**Warum?**
*   **Timing Attacks:** Wir nutzen `passwordEncoder.matches()`, was Zeitangriffe verhindert.
*   **Account Enumeration:** Durch die generische Meldung („E-Mail oder Passwort falsch“) erfährt ein Angreifer nicht, ob eine E-Mail-Adresse bei uns registriert ist oder nicht.

---

## Zusammenfassung für die Vorstellung:
*   **Sicherheitsstufe:** Top-Tier (Industriestandard).
*   **Komponenten:** Spring Security Crypto, BCrypt, Server-Side Configuration.
*   **Schutzziele:** Vertraulichkeit (Passwörter nicht lesbar) und Robustheit (gegen Offline-Cracking).
