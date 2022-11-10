package model.disease;

import model.employee.Employee;

import java.util.ArrayList;
import java.util.HashMap;


public class DiseaseCatalog {
    ArrayList<Disease> diseases;

    public DiseaseCatalog() {
        diseases = new ArrayList<>();
        loadCatalog();
    }

    public Disease newDisease(String name, boolean isContiguous, HashMap<String, String> symptons){
        Disease d = new Disease(name, isContiguous, symptons);
        diseases.add(d);
        return d;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }

    public void loadCatalog() {
        newDisease("HIV", true, new HashMap<String, String>() {{
            put("Temperature", "High");
            put("Thyroid", "High");
        }});
        newDisease("COVID-19", true, new HashMap<String, String>() {{
            put("Temperature", "High");
            put("SenseOfTaste", "False");
        }});
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Disease e : diseases) {
            sb.append(e + "\n");
        }
        return sb.toString();
    }
}
