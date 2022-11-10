package model.campaign;

import model.clinic.Clinic;
import model.clinic.ClinicCatalog;
import model.location.LocationCatalog;
import model.location.SiteCatalog;
import model.patient.PatientProfile;
import model.person.Person;

import java.util.*;

public class Campaign {
    String name;

    LocationCatalog locationCatalog;
    ClinicCatalog clinicCatalog;
    SiteCatalog siteCatalog;
    EventCatalog eventCatalog;
    HashMap<PatientProfile, Integer> deliberatelyInfectingOthersDict;
    HashSet<Person> potentialInfectedCandidates;

    public Campaign(String n, ClinicCatalog clinicCatalog, LocationCatalog locationCatalog, SiteCatalog siteCatalog) {
        name = n;
        this.clinicCatalog = clinicCatalog;
        this.locationCatalog = locationCatalog;
        this.siteCatalog = siteCatalog;
        eventCatalog = new EventCatalog(this);
        potentialInfectedCandidates = new HashSet<>();
        deliberatelyInfectingOthersDict = new HashMap<>();
    }

    public void configEvent(ArrayList<String> eventNames) {
        for (int i = 0; i < siteCatalog.getSites().size(); i++) {
            eventCatalog.newEvent(this, siteCatalog.getSites().get(i));
        }
    }

    public EventCatalog getEventCatalog() {
        return eventCatalog;
    }

    public void listAllPatients() {
        StringBuffer sb = new StringBuffer();   // StringBuffer can be modified many times without creating unused objects
        sb.append("List of all patient(s) and their last encounter time:\n");
        for (Clinic clinic : clinicCatalog.getClinics()) {  // two clinics
            for (PatientProfile patient : clinic.getPatientDirectory().getPatients()) {
                if (patient.getEncounterHistory().hasEncounter()
                        && patient.getEncounterHistory().getLastEncounter() != null) {
                    sb.append("Clinic: " + clinic.getName()
                            + " | " +  patient.getPerson()
                            + " | " + patient.getEncounterHistory().getLastEncounter() + "\n");
                }
            }
        }

        System.out.println(sb);
    }

    //check latest encounter has contiguous diseases
    public void listAllContiguousPatients() {
        StringBuffer sb = new StringBuffer();
        sb.append("List of all contiguous patient(s):\n");
        for (Clinic clinic : clinicCatalog.getClinics()) {
            for (PatientProfile patient : clinic.getPatientDirectory().getPatients()) {
                if (patient.getEncounterHistory().hasEncounter()
                        && patient.getEncounterHistory().getLastEncounter().hasContiguousDiseases()) {
                    sb.append("Clinic: " + clinic.getName()
                            + " | " + patient
                            + " | " + patient.getEncounterHistory().getLastEncounter().getContiguousDiseases()
                            + " | " + patient.getEncounterHistory().getLastEncounter().getCloseContacts() + "\n");
                }
            }
        }
        System.out.println(sb);
    }

    // hashmap key<patient profile> value<number of report times>
    // sum up all the deliberately infecting
    public void addDeliberatelyInfectingOthers(ArrayList<PatientProfile> patients) {
        for (PatientProfile patient : patients) {
            if (!deliberatelyInfectingOthersDict.containsKey(patient)) {
                deliberatelyInfectingOthersDict.put(patient, 0);
            }
            deliberatelyInfectingOthersDict.put(patient, deliberatelyInfectingOthersDict.get(patient) + 1);
        }
    }

    public void listDeliberatelyInfectingOthers() {
        StringBuffer sb = new StringBuffer();
        sb.append("List of patient(s) who deliberately infecting others:\n");
        //sort by reported times
        //iterate map entry
        for (Map.Entry<PatientProfile, Integer> en : sortByValue(deliberatelyInfectingOthersDict).entrySet()) {
            sb.append(en.getKey() + ": " + en.getValue() + " times \n");
        }
        if (deliberatelyInfectingOthersDict.isEmpty()) {
            sb.append("None of them!\n");
        }
        System.out.println(sb);
    }



    public void addPotentialInfectedPersons(ArrayList<Person> candidates) {
        for (Person person : candidates) {
            potentialInfectedCandidates.add(person);
        }
    }

    // remove the person who is already a patient
    public void listPotentialInfectedPersons() {
        StringBuffer sb = new StringBuffer();
        HashSet<Person> potentialInfectedPersons = (HashSet<Person>) potentialInfectedCandidates.clone();
        sb.append("List of potential infected person(s):\n");
        for (Person person : potentialInfectedCandidates) {
            for (Clinic clinic : clinicCatalog.getClinics()) {
                PatientProfile potentialPatient = clinic.getPatientDirectory().findPatient(person);
                if (potentialPatient != null && potentialInfectedPersons.contains(person)) {
                    potentialInfectedPersons.remove(person);
                }
            }
        }

        for (Person person : potentialInfectedPersons) {
            sb.append(person + "\n");
        }
        System.out.println(sb);
    }

    public void reportDeliberatelyInfectingOthersListToPolice() {
        // TODO
    }

    public HashSet<Person> contactPotentialInfectedIndividual() {
        return potentialInfectedCandidates;
    }

    // function to sort hashmap by values
    public static <T> HashMap<T, Integer> sortByValue(HashMap<T, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<T, Integer> > list =
                new LinkedList<Map.Entry<T, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<T, Integer> >() {
            public int compare(Map.Entry<T, Integer> o1,
                               Map.Entry<T, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<T, Integer> temp = new LinkedHashMap<T, Integer>();
        for (Map.Entry<T, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
