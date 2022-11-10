package model.patient;

import model.encounter.Encounter;
import model.encounter.EncounterHistory;
import model.person.Person;

public class PatientProfile {
    private PatientDirectory directory;
    private Person person;
    private EncounterHistory history;

    public PatientProfile(PatientDirectory directory, Person p) {
        this.directory = directory;
        this.person = p;
        history = new EncounterHistory(this);
    }

    public Person getPerson() {
        return person;
    }

    public EncounterHistory getEncounterHistory() {
        return history;
    }

    public String toString() {
        return "Patient: " + person;
    }
}
