/**
 * GGT Class (Grösster gemeinsamer Teiler) Berechnet den ggT von zwei Zahlen
 * @author Alessio Fano
 * @version 19.02.2026
 */
public class GGT {

    public static void main(String[] args) {
        int x = 28;
        int y = 20;
        System.out.println("Berechne ggT von " + x + " und " + y + ":");
        System.out.println("Ergebnis: " + gcd(x, y));
    }

    public static int gcd(int x, int y) {
        int rest = x % y;
        if (rest == 0) {
            return y; // Abbruchbedingung 
        } else {
            return gcd(y, rest); // Rekursiver Aufruf 
        }
    }
}

/**
 * Rekursive Methode zur Berechnung des ggT
 *
 * Beispiel für gcd(28, 20):
 *
 * gcd(28, 20)
 *    rest = 28 % 20 = 8
 *    rest != 0, also: gcd(20, 8)
 *       rest = 20 % 8 = 4
 *       rest != 0, also: gcd(8, 4)
 *          rest = 8 % 4 = 0
 *          rest == 0, also: return 4 ← Abbruch!
 *       ← return 4 (zurück)
 *    ← return 4 (zurück)
 * ← return 4 (Endergebnis)
 *
 * Die Rekursion "klettert" nach unten, bis x durch y teilbar ist (rest = 0).
 * Dann stoppt sie und gibt das Ergebnis zurück.
 */