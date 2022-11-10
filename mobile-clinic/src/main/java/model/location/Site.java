package model.location;

import model.clinic.Clinic;
import model.location.Location;

public class Site {
    String name;
    Location location;
    Clinic clinic;

    public Site(String n, Location l, Clinic c) {
        name = n;
        location = l;
        clinic = c;
    }
    
    public Clinic getClinic() {
        return clinic;
    }

    public String toString() {
        return "Site name: " + name + " | Site location: " + location;
    }
}
