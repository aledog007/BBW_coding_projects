package ch.bbw.cardgame.model;

/**
 * Car
 * Fachklasse für ein Auto
 * 
 * @author Peter Rutschmann
 * @date 26.08.2021
 */
public class Car {
    private String imageUrl;
    private String tradeName;
    private String model;
    private double prize;
    private double velocity;

    public Car(){};

    public Car(String imageUrl, String tradeName, String model, double prize, double velocity) {
        this.imageUrl = imageUrl;
        this.tradeName = tradeName;
        this.model = model;
        this.prize = prize;
        this.velocity = velocity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "Car{" +
              "imageUrl='" + imageUrl + '\'' +
              ", tradeName='" + tradeName + '\'' +
              ", model='" + model + '\'' +
              ", prize=" + prize +
              ", velocity=" + velocity +
              '}';
    }
}
