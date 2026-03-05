package ale.bbw;

public class ViererReihe {
    public static void main(String[] args) {
        System.out.println("--- Schleife (4er Reihe) ---");
        viererSchleife(10);

        System.out.println("\n--- Rekursion (4er Reihe) ---");
        viererRekursiv(10);
    }

    // Die Schleife
    public static void viererSchleife(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i + " x 4 = " + (i * 4));
        }
    }

    // Die Rekursion
    public static void viererRekursiv(int n) {
        if (n < 1) {
            return;
        }

        viererRekursiv(n - 1); // Treppe runter

        // Treppe rauf
        System.out.println(n  + " x 4 = " + (n * 4));
    }

}

/*
 * DIE TREPPE (4er Reihe):
 * i=10 -> aufruf(9)           |   Raufsteigen: Drucke 10 x 4
 * i=9  -> aufruf(8)           |   Raufsteigen: Drucke 9 x 4
 * ...                         |   ...
 * i=1  -> aufruf(0)           |   Raufsteigen: Drucke 1 x 4
 * ----------------------------|----------------------------
 * [Abbruch] i < 1 ------------> return; (Boden erreicht)
 */

