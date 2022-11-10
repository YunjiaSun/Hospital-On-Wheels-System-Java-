package model.campaign;

import java.util.Calendar;
import java.util.Date;

import model.employee.Employee;
import model.encounter.Encounter;
import model.location.Site;
import model.patient.PatientProfile;
import model.person.Person;

public class Event {
    Campaign campaign;
    Site site;

    Event(Campaign c, Site s) {
        campaign = c;
        site = s;
    }

    public Site getSite() {
        return site;
    }

    public Encounter checkinPatient(Person person, Employee nurse, Employee doctor) {

        //first find the random person in the clinic's patient
        PatientProfile patient = site.getClinic().getPatientDirectory().findPatient(person);

        if (patient == null) { //if can not find it - creat a new patient
            patient = site.getClinic().getPatientDirectory().newPatient(person);
        }

        Encounter encounter = patient.getEncounterHistory().newEncounter(Calendar.getInstance(), site, this, nurse, doctor);
        return encounter;
    }
}