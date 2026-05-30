package ale.bbw;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Aufgabe 3: Sitzverteilung im Nationalrat als Balkendiagramm
 *
 * Hier lesen wir die CSV-Datei mit allen gewählten Nationalraeten ein
 * und zählen wie viele Sitze jede Partei hat. Das Ergebnis zeigen wir
 * als Balkendiagramm sortiert nach Sitzanzahl.
 */
public class Aufgabe3_Nationalraete {

    public static void anzeigen() {

        // CSV über den ClassLoader laden, gleich wie bei Aufgabe 2
        try (InputStream is = Aufgabe3_Nationalraete.class.getClassLoader()
                .getResourceAsStream("chart-19-national-council-elected-ch-de.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is, StandardCharsets.UTF_8))) {

            // Alle gewaehlten Nationalraete nach Partei gruppieren und zählen.
            // In dieser CSV sind ALLE Werte in Anfuehrungszeichen, z.B.:
            // "Balmer Bettina","ZH","FDP","F","-","1"
            // Also müssen wir überall die Anführungszeichen wegmachen.

            Map<String, Long> sitzeProPartei = reader.lines()
                    // Header überspringen erste Zeile sind Spaltennamen
                    .skip(1)

                    // Leere Zeilen rausfiltern, manchmal hat die CSV am Ende
                    // noch Leerzeilen die stören würden
                    .filter(zeile -> !zeile.trim().isEmpty())

                    // Jede Zeile aufteilen - wir splitten am Komma
                    // Achtung: Wir nehmen hier an, dass die Werte selber keine
                    // Kommas enthalten (was bei dieser CSV zum Glück stimmt)
                    .map(zeile -> zeile.split(","))

                    // Nur Zeilen mit genug Spalten behalten
                    .filter(teile -> teile.length >= 3)

                    // Jetzt gruppieren wir nach der Partei (Spalte 2 = Index 2)
                    // und zählen wie oft jede Partei vorkommt.
                    // counting() ist perfekt dafür - es zählt einfach die Elemente
                    // in jeder Gruppe. Jede Zeile = ein Nationalrat = ein Sitz.
                    .collect(Collectors.groupingBy(
                            // Schlüssel: Parteiname, Anführungszeichen entfernen
                            teile -> teile[2].replace("\"", "").trim(),
                            // Wert: Anzahl zaehlen
                            Collectors.counting()
                    ));

            // Dataset für das Balkendiagramm aufbauen
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Wir sortieren die Parteien nach Sitzanzahl absteigend,
            // damit die grösste Partei ganz links im Diagramm steht.
            // comparingByValue() sortiert nach dem Wert (Anzahl Sitze)
            // und reversed() dreht die Reihenfolge um (groesste zuerst).
            sitzeProPartei.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(eintrag ->
                            dataset.addValue(
                                    eintrag.getValue(),     // Anzahl Sitze
                                    "Sitze",                // Serienname
                                    eintrag.getKey()        // Parteiname auf der x-Achse
                            )
                    );

            // Balkendiagramm erstellen
            JFreeChart chart = ChartFactory.createBarChart(
                    "Sitzverteilung Nationalrat Schweiz",   // Titel
                    "Partei",                                // x-Achse
                    "Anzahl Sitze",                          // y-Achse
                    dataset,
                    PlotOrientation.VERTICAL,
                    false,    // Legende braucht es nicht, haben ja nur eine Serie
                    true,     // Tooltips
                    false     // URLs
            );

            // Fenster anzeigen
            ChartFrame frame = new ChartFrame("Aufgabe 3 - Nationalrat", chart);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Nationalrat-CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
