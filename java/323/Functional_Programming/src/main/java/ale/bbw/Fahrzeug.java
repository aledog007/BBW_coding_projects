package ale.bbw;

/**
 * Klasse B – Fahrzeug (das Dienstfahrzeug vom Mitarbeiter)
 *
 * Wichtig: Die Klasse hat ihr EIGENES Comparable interface implementiert.
 * Das heisst der Vergleich passiert komplett hier drin und nicht in Mitarbeiter.
 * Mitarbeiter ruft einfach nur compareTo() auf und fertig.
 */
public class Fahrzeug implements Comparable<Fahrzeug> {

    private final String marke;
    private final String modell;
    private final int baujahr;
    private final int psLeistung;

    public Fahrzeug(String marke, String modell, int baujahr, int psLeistung) {
        this.marke = marke;
        this.modell = modell;
        this.baujahr = baujahr;
        this.psLeistung = psLeistung;
    }

    public String getMarke() {
        return marke;
    }

    public String getModell() {
        return modell;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public int getPsLeistung() {
        return psLeistung;
    }

    // HIER: Comparable<Fahrzeug> – mehrstufiger Vergleich
    // erst nach Marke, dann Baujahr, dann PS
    // das ist die eigene Vergleichslogik von Fahrzeug
    @Override
    public int compareTo(Fahrzeug other) {
        // zuerst Marke alphabetisch
        int ergebnis = this.marke.compareTo(other.marke);
        if (ergebnis != 0) {
            return ergebnis;
        }

        // dann Baujahr
        ergebnis = Integer.compare(this.baujahr, other.baujahr);
        if (ergebnis != 0) {
            return ergebnis;
        }

        // und zuletzt PS
        return Integer.compare(this.psLeistung, other.psLeistung);
    }

    @Override
    public String toString() {
        return marke + " " + modell + " (" + baujahr + ", " + psLeistung + " PS)";
    }
}
