package model.encounter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.campaign.Event;
import model.employee.Employee;
import model.location.Site;
import model.patient.PatientProfile;

public class EncounterHistory {
    ArrayList<Encounter> encounters;
    PatientProfile patient;

    public EncounterHistory(PatientProfile p){
        patient = p;
        encounters = new ArrayList<>();
    }

    public Encounter newEncounter(Calendar time, Site site, Event event, Employee nurse, Employee doctor) {
        Encounter e = new Encounter(this, time, site, event, patient, nurse, doctor);
        encounters.add(e);
        return e;
    }

    public boolean hasEncounter() {
        return !encounters.isEmpty();
    }

    public Encounter getLastEncounter() {
        return encounters.get(encounters.size() - 1);
    }

    public PatientProfile getPatient(){
        return patient;
    }
}
