package model.diagnosis;

import java.util.HashMap;

public class LabTest {
    String name;
    HashMap<String, Double> results;

    public LabTest(String n, BloodSample b) {
        name = n;
        results = new HashMap<>();
        analyseSample(b);
    }

    public void analyseSample(BloodSample bloodSample) {
        results.put("Thyroxine", bloodSample.getThyroxine());
        results.put("Triiodothyronine", bloodSample.getTriiodothyronine());
    }

    public HashMap<String, Double> getResults() {
        return results;
    }
}
