package model.patient;

import java.util.ArrayList;

import model.clinic.Clinic;
import model.employee.Employee;
import model.person.Person;

public class PatientDirectory {
    private Clinic clinic;
    private ArrayList<PatientProfile> patients;

    public PatientDirectory(Clinic c){
        clinic = c;
        patients = new ArrayList<PatientProfile>();
    }

    public PatientProfile newPatient(Person person){
        PatientProfile p = new PatientProfile(this,person);
        patients.add(p);
        return p;
    }

    public PatientProfile findPatient(Person person) {
        for (PatientProfile p : patients) {
            if (p.getPerson().getName().equals(person.getName()) && 
            p.getPerson().getDateOfBirth().equals(person.getDateOfBirth())) {
                return p;     
            }
        }
        return null;
    }

    public ArrayList<PatientProfile> getPatients() {
        return patients;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (PatientProfile p : patients) {
            sb.append(p + "\n");
        }
        return sb.toString();
    }
}
