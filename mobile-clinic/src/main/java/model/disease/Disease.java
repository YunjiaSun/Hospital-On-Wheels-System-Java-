package model.disease;

import java.util.HashMap;

public class Disease {
    String name;
    boolean isContiguous;
    HashMap<String, String> symptoms;

    Disease(String n, boolean c, HashMap<String, String> s) {
        name = n;
        isContiguous = c;
        symptoms = s;
    }

    public HashMap<String, String> getSymptoms() {
        return symptoms;
    }

    public boolean getIsContiguous() {
        return isContiguous;
    }

    public String toString() {
        return "Disease name: " + name + " | Contiguous: " + isContiguous;
    }
}
