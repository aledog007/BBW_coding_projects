package ale.bbw.coding;

public class Ausweis {
    private boolean isFamily;
    private int mitgliedNummer;
    private int myAttribute;

    public Ausweis(boolean isFamily, int  mitgliedNummer) {
        super();
        this.isFamily = isFamily;
        this.mitgliedNummer = mitgliedNummer;
        this.myAttribute = 100;
    }

    @Override
    public String toString() {
        return "Ausweis [isFamily=" + isFamily + ", mitgliedNummer="
               + mitgliedNummer + ", myAttribute=" + myAttribute + "]";
    }
}
