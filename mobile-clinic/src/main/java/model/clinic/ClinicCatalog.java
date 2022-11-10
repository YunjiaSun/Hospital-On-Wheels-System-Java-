package model.clinic;

import java.util.ArrayList;

public class ClinicCatalog {
    ArrayList<Clinic> clinics;

    public ClinicCatalog() {
        clinics = new ArrayList<>();
    }

    public Clinic newClinic(String name){
        Clinic c = new Clinic(name);
        clinics.add(c);
        return c;
    }

    public ArrayList<Clinic> getClinics() {
        return clinics;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Clinic c : clinics) {
            sb.append(c + "\n");
        }
        return sb.toString();
    }
}
