package model.encounter;

import java.util.ArrayList;
import java.util.Calendar;

import model.diagnosis.BloodSample;
import model.diagnosis.Diagnosis;
import model.diagnosis.LabTest;
import model.diagnosis.VitalSigns;
import model.disease.Disease;
import model.campaign.Event;
import model.employee.Employee;
import model.location.Site;
import model.patient.PatientProfile;
import model.person.Person;

public class Encounter {
    EncounterHistory encounterHistory;
    Calendar time;
    Site site;
    Event event;

    PatientProfile patient;
    Employee nurse;
    Employee doctor;

    String chiefComplaint;
    VitalSigns vitalSigns;
    LabTest labTest;

    ArrayList<Disease> diagnosisResults;
    ArrayList<Person> closeContacts;


    public Encounter(EncounterHistory encounterHistory, Calendar t, Site s, Event e, PatientProfile patient, Employee nurse, Employee doctor) {
        this.encounterHistory = encounterHistory;
        time = t;
        site = s;
        event = e;
        this.patient = patient;
        this.nurse = nurse;
        this.doctor = doctor;
        closeContacts = new ArrayList<>();
    }

    public Calendar getTime() {
        return time;
    }

    public void newVitalSigns(double weight, double height, double temp) {
        vitalSigns = new VitalSigns(weight, height, temp);
    }

    public void orderLabTest(String name, BloodSample bloodSample) {
        labTest = new LabTest(name, bloodSample);
    }

    public boolean hasDiagnosis() {
        return !diagnosisResults.isEmpty();
    }

    public void generateDiagnosis() {
        Diagnosis diagnosis = new Diagnosis(vitalSigns, labTest);
        diagnosisResults = diagnosis.diagnose();
    }

    public void printDiagnosisResults() {
        System.out.println(diagnosisResults);
    }

    public boolean hasContiguousDiseases() {
        for (Disease disease : diagnosisResults) {
            if (disease.getIsContiguous()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Disease> getContiguousDiseases() {
        ArrayList<Disease> contiguousDiseases = new ArrayList<>();
        for (Disease disease : diagnosisResults) {
            if (disease.getIsContiguous()) {
                contiguousDiseases.add(disease);
            }
        }
        return contiguousDiseases;
    }

    public void
    addCloseContacts(ArrayList<Person> contacts) {
        for (Person person : contacts) {
            closeContacts.add(person);
        }
    }

    public ArrayList<Person> getCloseContacts() {
        return closeContacts;
    }

    public ArrayList<PatientProfile> findDeliberatelyInfectPatients() {
        ArrayList<PatientProfile> deliberatelyInfectPatients = new ArrayList<>();

        // find these close contacts in this clinics's patient
        for (Person person : closeContacts) {
            PatientProfile knownPatient = site.getClinic().getPatientDirectory().findPatient(person);

            // find this patient & has encounter history
            // & diagnose a contiguous diseases at latest time
            // & the close contacts does not include this patient
            if (knownPatient != null
                    && knownPatient.getEncounterHistory().hasEncounter()
                    && knownPatient.getEncounterHistory().getLastEncounter().hasContiguousDiseases()
                    && !knownPatient.getEncounterHistory().getLastEncounter().getCloseContacts().contains(this.patient.getPerson())) {
                deliberatelyInfectPatients.add(knownPatient);
            }
        }
        return deliberatelyInfectPatients;
    }

    public PatientProfile getPatient(){
        return encounterHistory.getPatient();
    }

    public void recordChiefComplaint(String c) {
        chiefComplaint = c;
    }

    public String toString() {
        return "Visit time: " + time.get(Calendar.YEAR)
                + "-" + time.get(Calendar.MONTH)
                + "-" + time.get(Calendar.DAY_OF_MONTH)
                + " " + time.get(Calendar.HOUR)
                + ":" + time.get(Calendar.MINUTE)
                + ":" + time.get(Calendar.SECOND);
    }
}
