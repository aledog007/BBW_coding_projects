package ale.bbw;

public class Dreieckszahl {
        public static void main(String[] args) {
            int n = 5;

            System.out.println("--- Iterative Lösung (Schleife) ---");
            System.out.println("Ergebnis: " + summeSchleife(n));

            System.out.println("\n--- Rekursive Lösung (Treppe) ---");
            System.out.println("Ergebnis: " + summeRekursiv(n));
        }

        // mit schlaufe
        public static int summeSchleife(int n) {
            int ergebnis = 0;
            for (int i = 0; i <= n; i++) {
                ergebnis += i;
            }
            return ergebnis;
        }

        // mit rekursion
        public static int summeRekursiv(int n) {
            if (n <= 0) {
                return 0;
            }
            return summeRekursiv(n - 1) + n;
        }
}

/*
 * Die Treppe:
 * * Treppe runter (Aufrufe)        |   Treppe rauf (Rechnung)
 * -------------------------------|----------------------------------
 * n=5 -> summe(4) + 5            |   (10 + 5) = 15
 * n=4 -> summe(3) + 4          |   (6 + 4)  = 10
 * n=3 -> summe(2) + 3        |   (3 + 3)  = 6
 * n=2 -> summe(1) + 2      |   (1 + 2)  = 3
 * n=1 -> summe(0) + 1    |   (0 + 1)  = 1
 * -------------------------------|----------------------------------
 * n=0 (Check!) --------> return 0 (Boden der Treppe)
 * * Merkmal: Zuerst Treppe runter, dann Treppe rauf.
 */

// Abbruchkriterium (Der Check / n == 1 oder n == 0)