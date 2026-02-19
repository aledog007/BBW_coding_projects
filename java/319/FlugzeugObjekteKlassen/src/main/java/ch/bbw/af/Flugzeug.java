package ch.bbw.af;

import java.awt.*;

/**
 * Flugzeug
 * @author Alessio Fano
 * @version 21 September 2023
 */
public class Flugzeug {

    // Constructor ohne Parameter
        private String brand;
        private int sit;
        private Color color;

        public Flugzeug() {
            brand = "undefined";
            sit = -1;
            color = color.black;
        }


        // Constructor mit Parameter siehe nach public Cars -> ( "Parameter")
        public Flugzeug(String brand, int sit, Color color) {
            this.brand = brand;
            this.sit = sit;
            this.color = color;
        }

        //Getter/Setter
        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getSit() {
            return sit;
        }

        public void setSit(int sit) {
            this.sit = sit;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }


        // ToString Methode
        @Override
        public String toString() {
            return "Flugzeug{" +
                    "brand='" + getBrand() + '\'' +
                    ", sit=" + getSit() +
                    ", color=" + getColor() +
                    '}';
        }
    }

