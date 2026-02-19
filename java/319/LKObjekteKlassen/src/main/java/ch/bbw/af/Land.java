package ch.bbw.af;


import java.awt.*;

/**
 * Land
 * @author Alessio Fano
 * @version 5 Oktober 2023
 */

public class Land {

    // Constructor ohne Parameter
    private String Name ;
    private int Einwohnerzahl;
    private String Essen ;

    public Land() {
        Name = "undefined";
        Einwohnerzahl = -1;
        Essen = "Annanas";
    }


    // Constructor mit Parameter
    public Land(String brand, int Einwohnerzahl, String Essen) {
        this.Name = Name();
        this.Einwohnerzahl = Einwohnerzahl;
        this.Essen = Essen;
    }

    //Getter/Setter
    public String Name() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getEinwohnerzahl() {
        return Einwohnerzahl;
    }

    public void setEinwohnerzahl(int Einwohnerzahl) {
        this.Einwohnerzahl = Einwohnerzahl;
    }

    public String getEssen() {
        return Essen;
    }

    public void setEssen(String Essen) {
        this.Essen = Essen;
    }


    // ToString Methode
    @Override
    public String toString() {
        return "Land{" +
                "Name='" + getEssen() + '\'' +
                ", Einwohnerzahl=" + getEinwohnerzahl() +
                ", Essen=" + getEssen() +
                '}';
    }

}
