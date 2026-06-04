package ale.bbw;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

// 1. Wir erstellen die Klasse "Auto" als sauberen Record (vermeidet Boilerplate!)
record Auto(int ps) {
    // Java erstellt im Hintergrund automatisch die Methode "ps()" für uns
}

public class Main {
    // 2. Die Main-Methode muss exakt so deklariert sein:
    public static void main(String[] args) {

        // HIER IST DEINE LÖSUNG:
        // Wir übergeben den funktionalen Comparator DIREKT beim Erstellen der TreeMap.
        // Das "Auto::ps" sagt Java: Sortiere die Keys dieser Map nach den PS der Autos!
        SortedMap<Auto, String> demo = new TreeMap<>(Comparator.comparing(Auto::ps).reversed());

        // Beim Hinzufügen sortiert sich die TreeMap jetzt im Hintergrund komplett von alleine!
        demo.put(new Auto(10), "BMW");
        demo.put(new Auto(5), "Mercedes");

        // Ausgabe auf der Konsole
        System.out.println(demo);
        // Ergebnis: {Auto[ps=5]=Mercedes, Auto[ps=10]=BMW} -> Mercedes (5 PS) ist jetzt automatisch vor BMW (10 PS)!
    }
}