package ui;

import data.DataLoader;
import model.campaign.Campaign;
import model.campaign.Event;
import model.clinic.ClinicCatalog;
import model.diagnosis.BloodSample;
import model.diagnosis.VitalSigns;
import model.employee.Employee;
import model.encounter.Encounter;
import model.location.LocationCatalog;
import model.location.SiteCatalog;
import model.person.Person;

import java.util.ArrayList;
import java.util.Arrays;


public class MobileClinicMain {
    public static void main(String[] args) {

        // locale - standard
        DataLoader loader = new DataLoader("en-US");

        // creat fake person　
        loader.addNonPatients();

        // two clinics(hospital systems) - three cities/three locations - each location have two sites
        // creat 10 patients & two doctors & two nurses for each clinic
        ClinicCatalog clinicCatalog = loader.generateClinicCatalog(loader.getClinic());

        LocationCatalog locationCatalog = loader.generateLocationCatalog(loader.getCities(3));

        SiteCatalog siteCatalog = loader.generateSiteCatalog();

        Campaign campaign = loader.generateCampaign("Asian Community Matter", clinicCatalog, locationCatalog, siteCatalog);
        campaign.configEvent(loader.generateEventNames()); // 6 site - 6 events


        int mimicVisits = 100;

        // creat 100 fake complaints
        ArrayList<String> fakeComplaints = loader.generateChefComplaints(mimicVisits);
        // creat 100 fake vital signs
        ArrayList<int[]> fakeVitalSigns = loader.generateVitalSigns(mimicVisits);
        // creat 100 fake blood samples
        ArrayList<double[]> fakeBloodSamples = loader.generateBloodSamples(mimicVisits);

        for (int i = 0; i < mimicVisits; i++) {
            Event event = loader.getRandomEvent(campaign);
            Person visitor = loader.getRandomPerson(event);
            Employee nurse = loader.getRandomNurse(event);
            Employee doctor = loader.getRandomDoctor(event);

            // use a visitor/doctor/nurse to creat an encounter
            Encounter encounter = event.checkinPatient(visitor, nurse, doctor);

            // get a fake complaint
            encounter.recordChiefComplaint(fakeComplaints.get(i));

            // get a fake vital signs - weight, height, temp
            int[] fakeVitalSign = fakeVitalSigns.get(i);
            encounter.newVitalSigns(fakeVitalSign[0], fakeVitalSign[1], fakeVitalSign[2]);

            // get a fake blood sample - whiteBloodCell, thyroxine, triiodothyronine
            double[] fakeBloodSample = fakeBloodSamples.get(i);
            encounter.orderLabTest("BloodTest", new BloodSample(fakeBloodSample[0], fakeBloodSample[1], fakeBloodSample[2]));

            // get diagnosis - check&match
            encounter.generateDiagnosis();

            if (encounter.hasContiguousDiseases()) {

                //ask patient provide close contacts
                ArrayList<Person> fakeCloseContactPersons = loader.generateCloseContactPersons(event);

                //add the person of list into this encounter
                encounter.addCloseContacts(fakeCloseContactPersons);

                //add these potential infected persons
                campaign.addPotentialInfectedPersons(fakeCloseContactPersons);

                //add deliberately infecting others
                campaign.addDeliberatelyInfectingOthers(encounter.findDeliberatelyInfectPatients());
            }
        }


        campaign.listAllPatients();

        campaign.listAllContiguousPatients();

        campaign.listDeliberatelyInfectingOthers();

        campaign.listPotentialInfectedPersons();

        campaign.reportDeliberatelyInfectingOthersListToPolice();

        campaign.contactPotentialInfectedIndividual();

    }
}
