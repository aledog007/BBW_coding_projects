/**
 * Palindrom Class
 * @author Alessio Fano
 * @version 19.02.2026
 */
public class Palindrom {

    public static void main(String[] args) {
        String wort = "OTTO";
        char[] data = wort.toLowerCase().toCharArray();

        System.out.println("Überprüfe: " + wort);
        if (isPalindrom(data, 0, data.length - 1)) {
            System.out.println("Die Eingabe ist ein Palindrom");
        } else {
            System.out.println("Die Eingabe ist kein Palindrom");
        }
    }

    public static boolean isPalindrom(char[] data, int front, int rear) {
        // Abbruchbedingung
        if (front >= rear) {
            return true;
        }
        // Rekursiver Schritt
        if (data[front] == data[rear]) {
            return isPalindrom(data, front + 1, rear - 1);
        }
        return false;
    }
}


    /**
     * Rekursive Prüfung auf Palindrom
     *
     * Beispiel für "OTTO" (→ "otto" = ['o','t','t','o']):
     *
     * isPalindrom(['o','t','t','o'], 0, 3)
     *    data[0] = 'o' == data[3] = 'o'? JA ✓
     *    also: isPalindrom(['o','t','t','o'], 1, 2)
     *       data[1] = 't' == data[2] = 't'? JA ✓
     *       also: isPalindrom(['o','t','t','o'], 2, 1)
     *          front (2) >= rear (1)? JA ✓
     *          → return true (Abbruch!)
     *       ← return true (zurück)
     *    ← return true (zurück)
     * ← return true (Endergebnis: Es ist ein Palindrom!)
     *
     * Die Rekursion prüft von außen nach innen:
     * - front und rear nähern sich immer mehr an
     * - Sobald front >= rear, sind alle Zeichen überprüft → true
     * - Wenn irgendwann zwei Zeichen nicht passen → false
     */