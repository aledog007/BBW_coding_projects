package ale.bbw.coding;

public class Quadrat extends Form {
    private double seitenlaenge;

    public Quadrat(double seitenlaenge) {
        this.seitenlaenge = seitenlaenge;
    }

    @Override
    public double getFlaeche() {
        return seitenlaenge * seitenlaenge;
    }

    @Override
    public double getUmfang() {
        return 4 * seitenlaenge;
    }
}
