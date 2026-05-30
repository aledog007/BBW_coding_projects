package ale.bbw;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Hauptprogramm – hier wird alles zusammengeführt
 * alle Sortierkonzepte werden hier demonstriert
 *
 * Doku liegt in docs/DeepDive_Sortierung.md
 */
public class Main {

    // namen und autos für die testdaten
    private static final String[] VORNAMEN = {
            "Anna", "Ben", "Clara", "David", "Elena", "Felix", "Greta", "Hans",
            "Irene", "Jonas", "Karin", "Lukas", "Maria", "Nils", "Olivia",
            "Paul", "Quinn", "Rosa", "Stefan", "Tina", "Uwe", "Vera", "Walter",
            "Xenia", "Yannik", "Zoe", "Adrian", "Britta", "Cedric", "Diana"
    };

    private static final String[] NACHNAMEN = {
            "Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer",
            "Wagner", "Becker", "Schulz", "Hoffmann", "Koch", "Richter",
            "Wolf", "Schröder", "Neumann", "Schwarz", "Braun", "Zimmermann",
            "Krüger", "Hartmann", "Lange", "Werner", "Lehmann", "König"
    };

    private static final String[] AUTO_MARKEN = {"BMW", "Audi", "Mercedes", "VW", "Porsche", "Tesla", "Volvo"};
    private static final String[][] AUTO_MODELLE = {
            {"3er", "5er", "X3", "X5"},
            {"A3", "A4", "A6", "Q5"},
            {"C-Klasse", "E-Klasse", "GLC", "A"},
            {"Golf", "Passat", "Tiguan", "ID.4"},
            {"Cayenne", "Macan", "Taycan", "911"},
            {"Model 3", "Model Y", "Model S"},
            {"XC40", "XC60", "XC90", "S60"}
    };

    public static void main(String[] args) {
        System.out.println("Funktionales Sortieren von strukturierten Daten");
        System.out.println("Mitarbeiter & Dienstfahrzeuge");
        System.out.println();

        // testdaten generieren
        List<Mitarbeiter> mitarbeiterListe = generateTestData();
        System.out.println(mitarbeiterListe.size() + " Datensaetze generiert.");
        System.out.println("(ja alle 100 und die sind sicher nicht selber ausgedacht :)");
        System.out.println();

        // 1. Natural Order
        demonstriereNaturalOrder(mitarbeiterListe);

        // Reverse Order
        demonstriereReverseOrder(mitarbeiterListe);

        // 2. eigene Comparator-Klasse
        demonstriereComparatorKlasse(mitarbeiterListe);

        // 3. Anonyme Klasse
        demonstriereAnonymeKlasse(mitarbeiterListe);

        // 4. Lambda
        demonstriereLambdaExpression(mitarbeiterListe);

        // 5. Comparator Chain
        demonstriereComparatorChain(mitarbeiterListe);

        // 6. Comparator Konstante
        demonstriereComparatorKonstante(mitarbeiterListe);

        // Assoziation über Klasse B
        demonstriereAssoziationsSortierung(mitarbeiterListe);

        System.out.println("Dokumentation: siehe docs/DeepDive_Sortierung.md");
        System.out.println("// das war's, danke fürs durchschauen :)");
    }

    /**
     * generiert 100 zufällige mitarbeiter mit zufälligem fahrzeug etc.
     * seed ist fix damit die daten immer gleich rauskommen
     */
    public static List<Mitarbeiter> generateTestData() {
        List<Mitarbeiter> liste = new ArrayList<>();
        Random random = new Random(42); // fixer seed -> immer gleiche daten
        Abteilung[] abteilungen = Abteilung.values();

        for (int i = 0; i < 100; i++) {
            String name = VORNAMEN[random.nextInt(VORNAMEN.length)] + " "
                    + NACHNAMEN[random.nextInt(NACHNAMEN.length)];

            // gehalt zwischen 4000 und 15000, auf 100er gerundet
            double gehalt = Math.round((4000 + random.nextDouble() * 11000) / 100.0) * 100.0;

            // eintrittsdatum irgendwann zwischen 2005 und 2024
            LocalDate eintrittsDatum = LocalDate.of(2005, 1, 1).plusDays(random.nextInt(365 * 20));

            Abteilung abteilung = abteilungen[random.nextInt(abteilungen.length)];

            // zufälliges auto
            int markenIndex = random.nextInt(AUTO_MARKEN.length);
            Fahrzeug fahrzeug = new Fahrzeug(
                    AUTO_MARKEN[markenIndex],
                    AUTO_MODELLE[markenIndex][random.nextInt(AUTO_MODELLE[markenIndex].length)],
                    2018 + random.nextInt(8),
                    100 + random.nextInt(300)
            );

            liste.add(new Mitarbeiter(name, gehalt, eintrittsDatum, abteilung, fahrzeug));
        }

        return liste;
    }

    // gibt die ganze liste aus, nicht nur 10
    private static void druckeAuszug(List<Mitarbeiter> liste) {
        for (int i = 0; i < liste.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + liste.get(i));
        }
        System.out.println();
    }

    // HIER: Comparable – Natural Order (mehrstufig)
    // nutzt das compareTo() aus Mitarbeiter -> Name, Eintrittsdatum, Gehalt
    private static void demonstriereNaturalOrder(List<Mitarbeiter> original) {
        System.out.println("--- 1. Comparable - Natural Order (mehrstufig) ---");
        System.out.println("Sortierung: Name -> Eintrittsdatum -> Gehalt");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);
        Collections.sort(sortiert); // hier wird compareTo() aufgerufen

        druckeAuszug(sortiert);
    }

    // HIER: Reverse Order – dreht die Natural Order einfach um
    private static void demonstriereReverseOrder(List<Mitarbeiter> original) {
        System.out.println("--- Reverse Order ---");
        System.out.println("Sortierung: alles umgekehrt zur Natural Order");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);
        sortiert.sort(Comparator.reverseOrder());

        druckeAuszug(sortiert);
    }

    // HIER: Eigenständige Comparator-Klasse (mehrstufig)
    // die klasse MitarbeiterGehaltComparator macht den vergleich
    private static void demonstriereComparatorKlasse(List<Mitarbeiter> original) {
        System.out.println("--- 2. Eigenstaendige Comparator-Klasse (mehrstufig) ---");
        System.out.println("MitarbeiterGehaltComparator: Gehalt absteigend -> Name -> Eintrittsdatum");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);
        sortiert.sort(new MitarbeiterGehaltComparator());

        druckeAuszug(sortiert);
    }

    // HIER: Anonyme Klasse (mehrstufig)
    // comparator wird direkt als anonyme klasse erstellt, ohne eigene datei
    private static void demonstriereAnonymeKlasse(List<Mitarbeiter> original) {
        System.out.println("--- 3. Anonyme Klasse (mehrstufig) ---");
        System.out.println("Sortierung: Abteilung -> Eintrittsdatum -> Name");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);

        sortiert.sort(new Comparator<Mitarbeiter>() {
            @Override
            public int compare(Mitarbeiter m1, Mitarbeiter m2) {
                // zuerst abteilung
                int ergebnis = m1.getAbteilung().name().compareTo(m2.getAbteilung().name());
                if (ergebnis != 0) return ergebnis;

                // dann eintrittsdatum
                ergebnis = m1.getEintrittsDatum().compareTo(m2.getEintrittsDatum());
                if (ergebnis != 0) return ergebnis;

                // und dann name
                return m1.getName().compareTo(m2.getName());
            }
        });

        druckeAuszug(sortiert);
    }

    // HIER: Lambda Expression (mehrstufig)
    // das gleiche wie anonyme klasse aber viel kürzer geschrieben
    private static void demonstriereLambdaExpression(List<Mitarbeiter> original) {
        System.out.println("--- 4. Lambda Expression (mehrstufig) ---");
        System.out.println("Sortierung: Eintrittsdatum -> Gehalt absteigend -> Name");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);

        sortiert.sort((m1, m2) -> {
            // eintrittsdatum zuerst
            int ergebnis = m1.getEintrittsDatum().compareTo(m2.getEintrittsDatum());
            if (ergebnis != 0) return ergebnis;

            // gehalt absteigend
            ergebnis = Double.compare(m2.getGehalt(), m1.getGehalt());
            if (ergebnis != 0) return ergebnis;

            // name als letztes
            return m1.getName().compareTo(m2.getName());
        });

        druckeAuszug(sortiert);
    }

    // HIER: Comparator Chain (mehrstufig)
    // mit comparing() und thenComparing() einfach aneinander kettten
    private static void demonstriereComparatorChain(List<Mitarbeiter> original) {
        System.out.println("--- 5. Comparator Chain (mehrstufig) ---");
        System.out.println("Sortierung: Abteilung -> Gehalt absteigend -> Name -> Eintrittsdatum");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);

        Comparator<Mitarbeiter> chain = Comparator
                .comparing((Mitarbeiter m) -> m.getAbteilung().name())
                .thenComparing(Comparator.comparingDouble(Mitarbeiter::getGehalt).reversed())
                .thenComparing(Mitarbeiter::getName)
                .thenComparing(Mitarbeiter::getEintrittsDatum);

        sortiert.sort(chain);

        druckeAuszug(sortiert);
    }

    // HIER: Comparator-Attribut als Konstante in der Datenklasse
    // der comparator ist direkt in Mitarbeiter als public static final definiert
    private static void demonstriereComparatorKonstante(List<Mitarbeiter> original) {
        System.out.println("--- 6. Comparator-Konstante aus Mitarbeiter ---");
        System.out.println("Mitarbeiter.NACH_ABTEILUNG_UND_GEHALT: Abteilung -> Gehalt absteigend");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);
        sortiert.sort(Mitarbeiter.NACH_ABTEILUNG_UND_GEHALT);

        druckeAuszug(sortiert);
    }

    // HIER: Sortierung über Assoziation (Klasse B)
    // wichtig: wir greifen NICHT auf die attribute von Fahrzeug zu
    // sondern rufen nur compareTo() von Fahrzeug auf
    // die ganze vergleichslogik bleibt in Fahrzeug gekapselt
    private static void demonstriereAssoziationsSortierung(List<Mitarbeiter> original) {
        System.out.println("--- Erweiterte Anforderung I - Sortierung ueber Klasse B (Fahrzeug) ---");
        System.out.println("Sortierung: Fahrzeug (via compareTo -> Marke, Baujahr, PS) -> Name");
        System.out.println("(kein getter auf fahrzeug-attribute, nur compareTo!)");

        List<Mitarbeiter> sortiert = new ArrayList<>(original);

        // hier wird einfach nur fahrzeug.compareTo() aufgerufen
        // was drin passiert ist sache von Fahrzeug
        sortiert.sort((m1, m2) -> {
            int ergebnis = m1.getFahrzeug().compareTo(m2.getFahrzeug());
            if (ergebnis != 0) return ergebnis;

            return m1.getName().compareTo(m2.getName());
        });

        druckeAuszug(sortiert);
    }
}
