package model.location;

public class Location {
    String name;

    Location(String n) {
        name = n;
    }

    public String toString() {
        return "Location name: " + name + "\n";
    }
}