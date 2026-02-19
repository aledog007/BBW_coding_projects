package ale.bbw.coding;

public class Dreieck extends Form {
    private double a, b, c;

    public Dreieck(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getFlaeche() {
        double s = (a + b + c) / 2; // Halber Umfang
        return Math.sqrt(s * (s - a) * (s - b) * (s - c)); // Satz des Heron
    }

    @Override
    public double getUmfang() {
        return a + b + c;
    }
}
