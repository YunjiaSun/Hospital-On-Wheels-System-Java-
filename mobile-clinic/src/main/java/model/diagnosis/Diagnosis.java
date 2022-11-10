package model.diagnosis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.disease.Disease;
import model.disease.DiseaseCatalog;

public class Diagnosis {
    DiseaseCatalog diseaseCatalog;
    HashMap<String, String> patientSymptoms;
    private VitalSigns vitalSigns;
    private LabTest labTest;
    private double HIGH_TEMP_THRESHOLD = 98.6;
    private double LOW_TEMP_THRESHOLD = 95.0;

    //Thyroid enlargement is an obvious symptom of HIV
    //TSH (Thyroid-stimulating hormone) Test--between 0.4 and 4.5 milliunits per liter (mU/L)
    private double HIGH_TSH_LEVEL = 4.5;
    private double LOW_TSH_LEVEL = 0.4;

    public Diagnosis(VitalSigns v, LabTest l) {
        patientSymptoms = new HashMap<>();
        diseaseCatalog = new DiseaseCatalog();
        vitalSigns = v;
        labTest = l;
    }

    public ArrayList<Disease> diagnose() {
        checkTemperature(vitalSigns);
        checkThyroid(labTest);

        ArrayList<Disease> diseases = matchDisease();
        return diseases;
        
    }

    private void checkTemperature(VitalSigns vitalSigns) {
        if (vitalSigns.getTemp() < LOW_TEMP_THRESHOLD) {
            patientSymptoms.put("Temperature", "Low");
        } else if (vitalSigns.getTemp() > HIGH_TEMP_THRESHOLD) {
            patientSymptoms.put("Temperature", "High");
        } else {
            patientSymptoms.put("Temperature", "Normal");
        }
    }

    private void checkThyroid(LabTest labTest) {
        HashMap<String, Double> results = labTest.getResults();
        if (results.get("Thyroxine") > HIGH_TSH_LEVEL && results.get("Triiodothyronine") > HIGH_TSH_LEVEL) {
            patientSymptoms.put("Thyroid", "High");
        } else {
            patientSymptoms.put("Thyroid", "Low");
        }
    }

//
//     {Temperature -> Low, Thyroid -> Low, SenseOfTaste -> False}
//
//     [
//     {Temperature -> High, Thyroid -> High},
//     {Temperature -> High, SenseOfTaste -> False}
//     ]
//
//     isAllMatches -> true
//     HashMap{Temperature -> High, Thyroid -> High} ->
//     Set{
//           Map.Entry{Temperature -> High},
//           Map.Entry{Thyroid -> High}
//        }
//
//         set -> Map.Entry{Temperature -> High}
//         set.getKey() -> Temperature
//         set.getValue() -> High
//         patientValue -> Low
//
//

    private ArrayList<Disease> matchDisease() {
        ArrayList<Disease> diseases = new ArrayList<>();
        for (Disease disease : diseaseCatalog.getDiseases()) {
            boolean isAllMatches = true;
            for (Map.Entry<String, String> entry : disease.getSymptoms().entrySet()) {
                String patientValue = patientSymptoms.get(entry.getKey());

                if (!entry.getValue().equals(patientValue)) {
                    isAllMatches = false;
                    break;
                }
            }

            if (isAllMatches) {
                diseases.add(disease);
            }
        }
        return diseases;
    }
}
