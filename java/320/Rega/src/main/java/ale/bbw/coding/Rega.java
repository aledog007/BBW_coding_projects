package ale.bbw.coding;

import java.util.Arrays;

public class Rega {
    private String anschrift;
    private Ausweis[] ausweise;
    private Goenner[] goenner;
    private int myAttribute;

    public Rega(String anschrift) {
        super();
        this.anschrift = anschrift;
        this.myAttribute = 100;

        ausweise = new Ausweis[] {
                new Ausweis(true, 10),
                new Ausweis(false, 11),
                new Ausweis(true, 12),
                new Ausweis(false, 13),
        };
    }

    public void setGoenner(Goenner[] goenner) {
        this.goenner = goenner;

        goenner[0].setAusweis(ausweise[0]);
        goenner[1].setAusweis(ausweise[1]);
        goenner[2].setAusweis(ausweise[2]);
    }

    @Override
    public String toString() {
        return "Rega [anschrift=" + anschrift + ", ausweise="
                + Arrays.toString(ausweise) + ", goenner="
                + Arrays.toString(goenner) + ", myAttribute=" + myAttribute + "]";
    }

    public Ausweis getAusweise(int index) throws Exception {
        if (ausweise[index] == null) {
            throw new Exception();
        }
        return ausweise[index];
    }
}
