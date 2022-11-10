package model.location;

import model.employee.Employee;

import java.util.ArrayList;

public class LocationCatalog {
    ArrayList<Location> locations;

    public LocationCatalog(){
        locations = new ArrayList<>();
    }

    public Location newLocation(String name) {
        Location l = new Location(name);
        locations.add(l);
        return l;
    }
    
    public Location getLocation(String name){
        for (Location l: locations) {
            if (l.name.equals(name)) return l;
        }
        return null;
    }

    public ArrayList<Location> getLocations(){
        return locations;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Location l : locations) {
            sb.append(l + "\n");
        }
        return sb.toString();
    }
}
