package ch.bbw.af;

import java.awt.*;
import java.lang.reflect.Constructor;

/**
 * Car
 * @author Alessio Fano
 * @version 14 September 2023
 */

// Constructor ohne Parameter
public class Cars {
    private String brand;
    private int PS;
    private double velocity;
    private Color color;

    // Constructor mit Parameter siehe nach public Cars -> ( "Parameter")
    public Cars(String brand, int PS, double velocity, Color color) {
        this.brand = brand;
        this.PS = PS;
        this.velocity = velocity;
        this.color = color;
    }

    //Getter/Setter
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPS() {
        return PS;
    }

    public void setPS(int PS) {
        this.PS = PS;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
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
        return "Cars{" +
                "brand='" + brand + '\'' +
                ", PS=" + PS +
                ", velocity=" + velocity +
                ", color=" + color +
                '}';
    }
}
