package ale.bbw;

/**
 * Hauptklasse - startet alle vier Aufgaben nacheinander.
 * Jede Aufgabe oeffnet ein eigenes Fenster mit einem Diagramm.
 */
public class Main {

    public static void main(String[] args) {

        // Aufgabe 1: Einfaches Balkendiagramm mit Lieblingsfaechern
        System.out.println("Starte Aufgabe 1: Einfache Grafik (Lieblingsfaecher)...");
        Aufgabe1_EinfacheGrafik.anzeigen();

        // Aufgabe 2: Tortendiagramm mit Wahlergebnissen aus Aeugst am Albis
        System.out.println("Starte Aufgabe 2: Wahlen Zuerich (Tortendiagramm)...");
        Aufgabe2_WahlenZuerich.anzeigen();

        // Aufgabe 3: Balkendiagramm mit Nationalrat-Sitzverteilung
        System.out.println("Starte Aufgabe 3: Nationalraete (Balkendiagramm)...");
        Aufgabe3_Nationalraete.anzeigen();

        // Aufgabe 4: Liniendiagramm mit Wetterdaten
        System.out.println("Starte Aufgabe 4: Wetterdaten (Liniendiagramm)...");
        Aufgabe4_Wetterdaten.anzeigen();

        System.out.println("\nAlle Aufgaben gestartet! Fenster schliessen zum Beenden.");
    }
}
