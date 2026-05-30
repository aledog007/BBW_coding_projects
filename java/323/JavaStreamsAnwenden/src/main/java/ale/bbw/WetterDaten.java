package ale.bbw;

import java.time.LocalDate;

// Ein Record ist wie eine Klasse, aber viel kürzer - Java macht automatisch
// den Konstruktor, getter, equals, hashCode und toString für uns.
// Perfekt für Daten die man nur speichern und lesen will, nicht veraendern.
public record WetterDaten(LocalDate datum, String stadt, double temperatur) {
}
