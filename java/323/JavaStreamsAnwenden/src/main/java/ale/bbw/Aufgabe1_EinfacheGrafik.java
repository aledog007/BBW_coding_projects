package ale.bbw;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Aufgabe 1: Einfache Grafik mit hardcoded Daten
 *
 * Hier erstellen wir ein Balkendiagramm das zeigt, welche Schulfächer
 * in der Klasse am beliebtesten sind. Die Daten sind direkt im Code
 * eingetragen (hardcoded), also kein CSV oder so.
 */
public class Aufgabe1_EinfacheGrafik {

    public static void anzeigen() {

        // Unser Dataset - das ist quasi die "Tabelle" die JFreeChart braucht
        // um das Diagramm zu zeichnen
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Hier packen wir unsere "Lieblingsfächer" Daten rein.
        // Map.entry() erstellt ein Schlüssel Wert Paar, also Fach -> Anzahl Stimmen.
        // Mit Stream.of() machen wir einen Stream aus diesen Paaren,
        // und mit forEach fügen wir jedes Paar ins Dataset ein.
        // Das ist schöner als 5 mal dataset.addValue() zu schreiben.
        Stream.of(
                Map.entry("Mathe", 8),
                Map.entry("Deutsch", 5),
                Map.entry("Englisch", 7),
                Map.entry("Sport", 12),
                Map.entry("Informatik", 10)
        ).forEach(eintrag ->
                // addValue braucht: Wert, Reihe (Serie), Kategorie (x-Achse)
                // "Stimmen" ist der Name unserer Datenreihe
                dataset.addValue(eintrag.getValue(), "Stimmen", eintrag.getKey())
        );

        // Jetzt erstellen wir das eigentliche Balkendiagramm
        // Die Parameter sind: Titel, x-Achse Beschriftung, y-Achse Beschriftung,
        // Dataset, Ausrichtung (vertikal), Legende anzeigen, Tooltips, URLs
        JFreeChart chart = ChartFactory.createBarChart(
                "Lieblingsfächer unserer Klasse",   // Titel oben
                "Fach",                               // x-Achse
                "Anzahl Schüler",                    // y-Achse
                dataset,                              // unsere Daten
                PlotOrientation.VERTICAL,             // Balken stehen aufrecht
                false,                                // keine Legende nötig, ist ja nur eine Serie
                true,                                 // Tooltips beim Hovern
                false                                 // keine URLs
        );

        // ChartFrame ist ein JFrame das schon alles für uns einrichtet,
        // wir müssen nur Titel und Chart angeben
        ChartFrame frame = new ChartFrame("Aufgabe 1 - Lieblingsfächer", chart);
        frame.setSize(800, 600);

        // Fenster in der Mitte vom Bildschirm platzieren
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
