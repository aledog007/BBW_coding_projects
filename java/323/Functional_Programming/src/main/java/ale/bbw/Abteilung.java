package ale.bbw;

// Enum für die Abteilungen im Unternehmen
// hab einfach die gängigsten Abteilungen genommen die mir eingefallen sind
public enum Abteilung {

    ENTWICKLUNG("Entwicklung"),
    VERTRIEB("Vertrieb"),
    PERSONAL("Personal"),
    FINANZEN("Finanzen"),
    MARKETING("Marketing"),
    IT("IT"),
    LOGISTIK("Logistik"),
    PRODUKTION("Produktion");

    private final String bezeichnung;

    Abteilung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public String toString() {
        return bezeichnung;
    }
}
