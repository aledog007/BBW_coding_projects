/**
 * GGT Class
 * @author Alessio Fano
 * @version 03.02.2026
 */
public class GGT {

    public static void main(String[] args) {
        int x = 28;
        int y = 20;
        System.out.println("Berechne ggT von " + x + " und " + y + ":");
        System.out.println("Ergebnis: " + gcd(x, y));
    }

    /**
     * Rekursive Methode zur Berechnung des ggT
     */
    public static int gcd(int x, int y) {
        int rest = x % y; // [cite: 190]
        if (rest == 0) {
            return y; // Abbruchbedingung 
        } else {
            return gcd(y, rest); // Rekursiver Aufruf 
        }
    }
}