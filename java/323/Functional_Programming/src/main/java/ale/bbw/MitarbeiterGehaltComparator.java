package ale.bbw;

import java.util.Comparator;

// HIER: Eigenständige Comparator-Klasse (mehrstufig)
// eigene Klasse die Comparator implementiert
// sortiert nach Gehalt absteigend, dann Name, dann Eintrittsdatum
public class MitarbeiterGehaltComparator implements Comparator<Mitarbeiter> {

    @Override
    public int compare(Mitarbeiter m1, Mitarbeiter m2) {
        // Gehalt absteigend -> deshalb m2 vor m1
        int ergebnis = Double.compare(m2.getGehalt(), m1.getGehalt());
        if (ergebnis != 0) {
            return ergebnis;
        }

        // dann Name alphabetisch
        ergebnis = m1.getName().compareTo(m2.getName());
        if (ergebnis != 0) {
            return ergebnis;
        }

        // und zuletzt Eintrittsdatum
        return m1.getEintrittsDatum().compareTo(m2.getEintrittsDatum());
    }
}
