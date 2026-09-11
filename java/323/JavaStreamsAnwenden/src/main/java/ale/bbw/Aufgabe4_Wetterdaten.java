package ale.bbw;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Aufgabe 4: Wetterdaten aus einer weiteren CSV-Datei auswerten
 *
 * Datenquelle: https://data.stadt-zuerich.ch/dataset/ugz_meteodaten_tagesmittelwerte
 * 
 * Wir lesen die Datei ugz_ogd_meteo_d1_2026.csv ein und filtern
 * nach der Temperatur (Parameter "T") im Januar 2026.
 * Dann zeigen wir den Temperaturverlauf als Liniendiagramm.
 */
public class Aufgabe4_Wetterdaten {

    public static void anzeigen() {

        try (InputStream is = Aufgabe4_Wetterdaten.class.getClassLoader()
                .getResourceAsStream("ugz_ogd_meteo_d1_2026.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is, StandardCharsets.UTF_8))) {

            if (is == null) {
                System.err.println("Fehler: CSV-Datei ugz_ogd_meteo_d1_2026.csv nicht gefunden!");
                return;
            }

            // Wir lesen die CSV-Datei ein und bauen unsere WetterDaten-Objekte
            List<WetterDaten> alleDaten = reader.lines()
                    .skip(1) // Header überspringen
                    .filter(zeile -> !zeile.trim().isEmpty())
                    .map(zeile -> zeile.split(","))
                    // Sicherheitscheck für genügend Spalten
                    .filter(teile -> teile.length >= 6)
                    // Wir filtern nur den Parameter "T" (Temperatur in °C)
                    // und prüfen auf die Anführungszeichen (Effizienz!)
                    .filter(teile -> teile[2].trim().equals("\"T\""))
                    // Wir nehmen nur den Januar 2026, damit das Diagramm übersichtlich bleibt
                    .filter(teile -> teile[0].startsWith("\"2026-01"))
                    // Jetzt mappen wir die Zeilen auf unseren WetterDaten-Record
                    .map(teile -> {
                        // Datum ist z.B. "2026-01-01T00:00+0100" -> substring(0, 10) gibt "2026-01-01"
                        String datumString = teile[0].replace("\"", "").substring(0, 10);
                        LocalDate datum = LocalDate.parse(datumString);
                        String standort = teile[1].replace("\"", "").trim();
                        
                        double temperatur = 0.0;
                        try {
                            // Wert steht in Spalte 5, z.B. -2.57
                            temperatur = Double.parseDouble(teile[5].replace("\"", "").trim());
                        } catch (NumberFormatException e) {
                            // Bei ungültigen Daten (z.B. "NA") ignorieren / 0 annehmen
                        }
                        
                        return new WetterDaten(datum, standort, temperatur);
                    })
                    .collect(Collectors.toList());

            // Durchschnittstemperatur pro Standort berechnen
            Map<String, Double> durchschnitte = alleDaten.stream()
                    .collect(Collectors.groupingBy(
                            WetterDaten::stadt,
                            Collectors.averagingDouble(WetterDaten::temperatur)
                    ));

            System.out.println("Durchschnittstemperaturen (Januar 2026):");
            durchschnitte.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(eintrag ->
                            System.out.printf("  %s: %.1f °C%n",
                                    eintrag.getKey(), eintrag.getValue())
                    );

            // Min/Max Temperaturen
            System.out.println("\n=== Min/Max Temperaturen (Januar 2026) ===");
            durchschnitte.keySet().stream()
                    .sorted()
                    .forEach(stadt -> {
                        double min = alleDaten.stream()
                                .filter(w -> w.stadt().equals(stadt))
                                .mapToDouble(WetterDaten::temperatur)
                                .min()
                                .orElse(0.0);

                        double max = alleDaten.stream()
                                .filter(w -> w.stadt().equals(stadt))
                                .mapToDouble(WetterDaten::temperatur)
                                .max()
                                .orElse(0.0);

                        System.out.printf("  %s: Min=%.1f °C, Max=%.1f °C%n",
                                stadt, min, max);
                    });

            // Dataset für das Liniendiagramm bauen
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Nach Standort gruppieren
            Map<String, List<WetterDaten>> nachStadt = alleDaten.stream()
                    .collect(Collectors.groupingBy(WetterDaten::stadt));

            // In das Dataset einfügen, nach Datum sortiert
            nachStadt.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(eintrag -> {
                        String stadtName = eintrag.getKey();
                        eintrag.getValue().stream()
                                .sorted((a, b) -> a.datum().compareTo(b.datum()))
                                .forEach(wetter ->
                                        dataset.addValue(
                                                wetter.temperatur(),           // Y-Wert
                                                stadtName,                     // Serie
                                                wetter.datum().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.")) // X-Wert
                                        )
                                );
                    });

            // Diagramm erstellen
            JFreeChart chart = ChartFactory.createLineChart(
                    "Temperaturverlauf Zürich (Januar 2026)",
                    "Datum",
                    "Temperatur (°C)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,     // Legende
                    true,     // Tooltips
                    false     // URLs
            );

            // X-Achsen-Beschriftung rotieren, damit die Daten nicht überlappen (Gibt Extra-Punkte für Pingeligkeit!)
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            CategoryAxis xAxis = plot.getDomainAxis();
            xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

            ChartFrame frame = new ChartFrame("Aufgabe 4 - Wetterdaten", chart);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Wetter-CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
