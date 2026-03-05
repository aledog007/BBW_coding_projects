package ale.bbw;

public class UngeradenZahlen {

        public static void main(String[] args) {
            int n = 10;

            System.out.println("--- Iterative Lösung (Schleife) ---");
            ausgabeUngeradeSchleife(n);

            System.out.println("\n--- Rekursive Lösung (Treppe) ---");
            ausgabeUngeradeRekursiv(n);
        }

        // Schleife
        public static void ausgabeUngeradeSchleife(int n) {
            for (int i = 1; i <= n; i++) {
                if (i % 2 != 0) {
                    System.out.println(i);
                }
            }
        }

        // Rekursion
        public static void ausgabeUngeradeRekursiv(int n) {

            if (n < 1) {
                return;
            }

            // Treppe runtersteigen
            ausgabeUngeradeRekursiv(n - 1);
            // Treppe raufsteigen und printen
            if (n % 2 != 0) {
                System.out.println(n);
            }
        }
}


/*
 * Die Treppe:
 * * Treppe runter        |   Treppe rauf
 * -------------------------------|----------------------------------
 * n=10 -> aufruf(9)              |   Prüfe 10: gerade -> nix
 * n=9  -> aufruf(8)              |   Prüfe 9: ungerade -> DRUCK 9
 * n=8  -> aufruf(7)              |   Prüfe 8: gerade -> nix
 * n=7  -> aufruf(6)              |   Prüfe 7: ungerade -> DRUCK 7
 * ...                            |   ...
 * n=1  -> aufruf(0)              |   Prüfe 1: ungerade -> DRUCK 1
 * -------------------------------|----------------------------------
 * [Abbruchkriterium] n < 1 ------> return; (Boden erreicht)
 * * WICHTIG: Da wir den Druck-Befehl NACH dem rekursiven Aufruf schreiben,
 * arbeitet das Programm die Zahlen beim "Raufsteigen" ab (1, 3, 5...).
 */