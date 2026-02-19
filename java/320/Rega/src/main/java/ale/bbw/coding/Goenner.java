package ale.bbw.coding;

public class Goenner {
    private String name;
    private Ausweis ausweis;
    private int myAttribute;

    public Goenner(String name) {
        super();
        this.name = name;
        this.myAttribute = 100;
    }

    public void setAusweis(Ausweis ausweis) {
        this.ausweis = ausweis;
    }

    @Override
    public String toString() {
        return "Mitglied " + name + ", " + ausweis;
    }
}
