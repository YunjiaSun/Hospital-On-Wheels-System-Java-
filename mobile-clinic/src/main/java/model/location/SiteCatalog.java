package model.location;

import model.clinic.Clinic;

import java.util.ArrayList;

public class SiteCatalog {
    private ArrayList<Site> sites;

    public SiteCatalog() {
        sites = new ArrayList<>();
    }

    public Site newSite(String name, Location location, Clinic clinic) {
        Site s = new Site(name, location, clinic);
        sites.add(s);
        return s;
    }

    public ArrayList<Site> getSites() {
        return sites;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Site s : sites) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }
}
