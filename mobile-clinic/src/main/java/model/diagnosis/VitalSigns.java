package model.diagnosis;

public class VitalSigns {
    private double weight;
    private double height;
    private double temp;

    public VitalSigns(double weight, double height, double temp){
        this.weight = weight;
        this.height = height;
        this.temp = temp;
    }

    public double getWeight(){
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getTemp() {
        return temp;
    }
}
