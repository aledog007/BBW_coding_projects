package ale.bbw;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Aufgabe 4: Wetterdaten als Liniendiagramm
 *
 * Hier generieren wir fiktive Temperaturdaten für drei Schweizer Staedte
 * und zeigen den Temperaturverlauf über 10 Tage als Liniendiagramm.
 * Ausserdem berechnen wir die Durchschnittstemperatur pro Stadt.
 *
 * Kein CSV diesmal - wir erzeugen die Daten direkt mit Streams!
 */
public class Aufgabe4_Wetterdaten {

    public static void anzeigen() {

        // Startdatum für unsere Wetterdaten
        LocalDate startDatum = LocalDate.of(2024, 6, 1);

        // Fiktive Basisttemperaturen für unsere drei Städte
        // Zürich ist ein bisschen wärmer, Basel auch, Bern etwas kühler
        // Die kleinen Schwankungen simulieren wir mit sin/cos - sieht realistischer aus
        // als wenn die Temperatur jeden Tag gleich wäre

        // Wir erzeugen die Daten mit flatMap.
        // Stream.of() gibt uns die drei Städte, und flatMap "klappt" für jede Stadt
        // 10 Tage auf. Das heisst aus 3 Elementen werden 30 (3 Städte x 10 Tage).
        List<WetterDaten> alleDaten = Stream.of(
                Map.entry("Zürich", 22.0),
                Map.entry("Bern", 20.0),
                Map.entry("Basel", 23.0)
        ).flatMap(stadt ->
                // IntStream.range(0, 10) gibt uns die Zahlen 0 bis 9
                // Für jeden Tag erstellen wir ein WetterDaten-Objekt
                IntStream.range(0, 10).mapToObj(tag -> {
                    // Temperatur berechnen: Basistemperatur + Schwankung
                    // sin() gibt Werte zwischen -1 und 1, mal 5 ergibt Schwankung von -5 bis +5 Grad
                    double temperatur = stadt.getValue()
                            + 5.0 * Math.sin(tag * 0.8 + stadt.getKey().length());
                    // Auf eine Nachkommastelle runden damit es sauberer aussieht
                    temperatur = Math.round(temperatur * 10.0) / 10.0;

                    return new WetterDaten(
                            startDatum.plusDays(tag),   // Datum hochzählen
                            stadt.getKey(),             // Stadtname
                            temperatur                  // berechnete Temperatur
                    );
                })
        ).collect(Collectors.toList());

        // Durchschnittstemperatur pro Stadt berechnen und ausgeben.
        // groupingBy gruppiert nach Stadt, und averagingDouble berechnet
        // den Durchschnitt über alle Temperaturen in der Gruppe.
        Map<String, Double> durchschnitte = alleDaten.stream()
                .collect(Collectors.groupingBy(
                        WetterDaten::stadt,
                        Collectors.averagingDouble(WetterDaten::temperatur)
                ));

        // Durchschnitte in der Konsole ausgeben
        System.out.println("Durchschnittstemperaturen:");
        durchschnitte.entrySet().stream()
                // Alphabetisch nach Stadtname sortieren, sieht ordentlicher aus
                .sorted(Map.Entry.comparingByKey())
                .forEach(eintrag ->
                        System.out.printf("  %s: %.1f °C%n",
                                eintrag.getKey(), eintrag.getValue())
                );

        // Jetzt noch die Min/Max Temperatur pro Stadt - einfach weil wir's können
        // und es zeigt wie mapToDouble funktioniert
        System.out.println("\n=== Min/Max Temperaturen ===");
        durchschnitte.keySet().stream()
                .sorted()
                .forEach(stadt -> {
                    // Alle Temperaturen dieser Stadt als DoubleStream
                    // mapToDouble wandelt den Objekt-Stream in einen double-Stream um
                    // Das ist effizienter und gibt uns min()/max() gerade mit
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

        // Liniendiagramm bauen - wir benutzen DefaultCategoryDataset
        // weil das mit createLineChart am einfachsten ist
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Daten nach Stadt gruppieren und dann chronologisch ins Dataset packen
        // Zuerst gruppieren wir nach Stadt
        Map<String, List<WetterDaten>> nachStadt = alleDaten.stream()
                .collect(Collectors.groupingBy(WetterDaten::stadt));

        // dann gehen wir durch jede Stadt und fügen die Tage sortiert ein
        nachStadt.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(eintrag -> {
                    String stadtName = eintrag.getKey();
                    // Für jede Stadt sortieren wir nach Datum und fügen
                    // die Temperatur als Datenpunkt ein
                    eintrag.getValue().stream()
                            .sorted((a, b) -> a.datum().compareTo(b.datum()))
                            .forEach(wetter ->
                                    dataset.addValue(
                                            wetter.temperatur(),           // Y-Wert
                                            stadtName,                     // Serie (Linie)
                                            wetter.datum().toString()      // X-Wert (Kategorie)
                                    )
                            );
                });

        // Liniendiagramm erstellen
        JFreeChart chart = ChartFactory.createLineChart(
                "Temperaturverlauf - Schweizer Städte",  // Titel
                "Datum",                                    // x-Achse
                "Temperatur (°C)",                          // y-Achse
                dataset,
                PlotOrientation.VERTICAL,
                true,     // Legende anzeigen - brauchen wir hier weil mehrere Städte
                true,     // Tooltips
                false     // URLs
        );

        // Fenster anzeigen
        ChartFrame frame = new ChartFrame("Aufgabe 4 - Wetterdaten", chart);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
