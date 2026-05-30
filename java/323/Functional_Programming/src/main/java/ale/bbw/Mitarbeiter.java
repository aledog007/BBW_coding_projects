package ale.bbw;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Klasse A – Mitarbeiter (Hauptklasse)
 *
 * Hat 5 Attribute mit 4 verschiedenen Datentypen:
 *   - name (String)
 *   - gehalt (double)
 *   - eintrittsDatum (LocalDate) -> nicht primitiv
 *   - abteilung (Abteilung/Enum) -> nicht primitiv
 *   - fahrzeug (Fahrzeug/Klasse B) -> nicht primitiv, Assoziation
 *
 * Implementiert Comparable für die Natural Order.
 */
public class Mitarbeiter implements Comparable<Mitarbeiter> {

    private final String name;
    private final double gehalt;
    private final LocalDate eintrittsDatum;
    private final Abteilung abteilung;
    private final Fahrzeug fahrzeug;

    // HIER: Comparator-Attribut als Konstante direkt in der Datenklasse
    // sortiert nach Abteilung und dann nach Gehalt absteigend
    public static final Comparator<Mitarbeiter> NACH_ABTEILUNG_UND_GEHALT =
            Comparator.comparing((Mitarbeiter m) -> m.abteilung.name())
                    .thenComparing(Comparator.comparingDouble((Mitarbeiter m) -> m.gehalt).reversed());

    public Mitarbeiter(String name, double gehalt, LocalDate eintrittsDatum,
                       Abteilung abteilung, Fahrzeug fahrzeug) {
        this.name = name;
        this.gehalt = gehalt;
        this.eintrittsDatum = eintrittsDatum;
        this.abteilung = abteilung;
        this.fahrzeug = fahrzeug;
    }

    public String getName() {
        return name;
    }

    public double getGehalt() {
        return gehalt;
    }

    public LocalDate getEintrittsDatum() {
        return eintrittsDatum;
    }

    public Abteilung getAbteilung() {
        return abteilung;
    }

    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    // HIER: Comparable – Natural Order mehrstufig
    // zuerst Name, dann Eintrittsdatum, dann Gehalt
    @Override
    public int compareTo(Mitarbeiter other) {
        int ergebnis = this.name.compareTo(other.name);
        if (ergebnis != 0) {
            return ergebnis;
        }

        ergebnis = this.eintrittsDatum.compareTo(other.eintrittsDatum);
        if (ergebnis != 0) {
            return ergebnis;
        }

        return Double.compare(this.gehalt, other.gehalt);
    }

    @Override
    public String toString() {
        return String.format("%-20s | %10.2f CHF | %-12s | %-12s | %s",
                name, gehalt, eintrittsDatum, abteilung, fahrzeug);
    }
}
