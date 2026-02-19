package ale.bbw.coding;

public class Kreis extends Form {
    private double radius;

    public Kreis(double radius) {
        this.radius = radius;
    }

    @Override
    public double getFlaeche() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getUmfang() {
        return 2 * Math.PI * radius;
    }
}

