package ale.bbw;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Aufgabe 2: Wahldaten aus dem Kanton Zürich als Tortendiagramm
 *
 * Wir lesen die CSV-Datei mit den Nationalratswahl-Ergebnissen
 * Am Ende zeigen wir ein Tortendiagramm mit den Prozentanteilen.
 */
public class Aufgabe2_WahlenZuerich {

    public static void anzeigen() {

        // Die CSV-Datei liegt in src/main/resources, also laden wir sie über den ClassLoader.
        // Das ist der richtige Weg in Java-Projekten mit Maven/Gradle,
        // weil die Datei dann auch im fertigen JAR gefunden wird.
        try (InputStream is = Aufgabe2_WahlenZuerich.class.getClassLoader()
                .getResourceAsStream("KTZH_00000693_00004943.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is, StandardCharsets.UTF_8))) {

            // Jetzt kommt der spannende Teil - wir verarbeiten die ganze CSV mit Streams!
            // Zürst lesen wir alle Zeilen, dann filtern, parsen und gruppieren wir.

            // Schritt 1: Alle Zeilen als Stream holen
            // Schritt 2: Header-Zeile überspringen mit skip(1)
            // Schritt 3: Jede Zeile am Komma aufteilen - ABER Achtung: manche Felder
            //            haben Anführungszeichen, die müssen wir entfernen
            // Schritt 4: Nur Zeilen behalten wo die Gemeinde ist
            // Schritt 5: Nach Partei gruppieren und die Stimmen aufsummieren
            Map<String, Long> stimmenProPartei = reader.lines()
                    // Erste Zeile ist der Header, den brauchen wir nicht
                    .skip(1)

                    // Jede Zeile in ein String-Array aufteilen
                    // Wir splitten am Komma, aber das ist etwas tricky weil
                    // manche Felder in Anführungszeichen stehen
                    .map(zeile -> zeile.split(","))

                    // Sicherheitscheck: Nur Zeilen nehmen die genug Spalten haben
                    // Sonst kriegen wir ArrayIndexOutOfBoundsException
                    .filter(teile -> teile.length >= 11)

                    // Hier filtern wir alle Zeilen raus, die nicht zu unserer Gemeinde gehören.
                    // Spalte 3 ist "Einheit_Name". Aus Effizienzgründen vergleichen wir
                    // direkt mit dem String inklusive Anführungszeichen, damit wir
                    // replace() nicht für jede einzelne Zeile im CSV ausführen müssen.
                    .filter(teile -> teile[3].trim().equals("\"Affoltern am Albis\""))

                    // Jetzt gruppieren wir nach Partei (Spalte 7) und summieren
                    // die Parteistimmen (Spalte 10) auf.
                    // groupingBy sagt: "Mach Gruppen nach diesem Kriterium"
                    // summingLong sagt: "Und für jede Gruppe, addiere diese Zahlen zusammen"
                    .collect(Collectors.groupingBy(
                            // Schlüssel: Parteiname ohne Anführungszeichen
                            teile -> teile[7].replace("\"", "").trim(),

                            // Wert: Summe der Parteistimmen (Spalte 10), auch Anführungszeichen weg
                            // Zur Sicherheit (Pingeligkeit) mit try-catch um leere oder ungültige Einträge abzufangen
                            Collectors.summingLong(teile -> {
                                try {
                                    return Long.parseLong(teile[10].replace("\"", "").trim());
                                } catch (NumberFormatException ex) {
                                    return 0L; // Bei fehlerhaften Daten einfach 0 addieren
                                }
                            })
                    ));

            // Gesamtstimmen berechnen das brauchen wir für die Prozentberechnung.
            // Wir nehmen alle Werte aus unserer Map, machen einen Stream draus,
            // und addieren alles mit reduce zusammen. reduce(0L, Long::sum) startet
            // bei 0 und addiert immer den nächsten Wert dazu.
            long gesamtStimmen = stimmenProPartei.values().stream()
                    .reduce(0L, Long::sum);

            // Jetzt baün wir das PieDataset zusammen
            DefaultPieDataset dataset = new DefaultPieDataset();

            // Wir gehen durch alle Einträge in unserer Map und fügen sie ins Dataset ein.
            // Dabei berechnen wir gleich den Prozentwert, damit man im Diagramm
            // die Parteinamen mit Prozent sieht.
            stimmenProPartei.entrySet().stream()
                    // Sortieren nach Stimmenanzahl absteigend, damit die grösste Partei
                    // zürst kommt - sieht im Diagramm besser aus
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(eintrag -> {
                        // Prozentwert berechnen
                        double prozent = (eintrag.getValue() * 100.0) / gesamtStimmen;
                        // Label mit Parteiname und Prozent erstellen
                        String label = eintrag.getKey() + " (" +
                                String.format("%.1f", prozent) + "%)";
                        dataset.setValue(label, eintrag.getValue());
                    });

            // Tortendiagramm erstellen
            JFreeChart chart = ChartFactory.createPieChart(
                    "Nationalratswahl 2023",  // Titel
                    dataset,                                      // Daten
                    true,                                         // Legende anzeigen
                    true,                                         // Tooltips
                    false                                         // URLs
            );

            // Wir passen die Labels an, damit man Prozent und den Wert sieht
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                    "{0}: {2}",  // {0}=Name, {1}=Wert, {2}=Prozent
                    new DecimalFormat("0"),
                    new DecimalFormat("0.0%")
            ));

            // Fenster erstellen und anzeigen
            ChartFrame frame = new ChartFrame("Aufgabe 2 - Wahlen Zürich", chart);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (IOException e) {
            // Wenn die Datei nicht gefunden wird, geben wir eine klare Fehlermeldung aus
            System.err.println("Fehler beim Lesen der Wahldaten-CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
