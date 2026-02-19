package ale.bbw.coding;

public class Main {
    public static void main(String[] args) {
        Quadrat quadrat = new Quadrat(4);
        Dreieck dreieck = new Dreieck(3, 4, 5);
        Kreis kreis = new Kreis(3);

        System.out.println("Quadrat Fläche: " + quadrat.getFlaeche());
        System.out.println("Quadrat Umfang: " + quadrat.getUmfang());

        System.out.println("Dreieck Fläche: " + dreieck.getFlaeche());
        System.out.println("Dreieck Umfang: " + dreieck.getUmfang());

        System.out.println("Kreis Fläche: " + kreis.getFlaeche());
        System.out.println("Kreis Umfang: " + kreis.getUmfang());
    }
}