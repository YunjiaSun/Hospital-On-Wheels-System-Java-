package data;

import java.util.*;

import com.github.javafaker.Faker;
import model.campaign.Campaign;
import model.campaign.Event;
import model.clinic.Clinic;
import model.clinic.ClinicCatalog;
import model.employee.Employee;
import model.employee.EmployeeRole;
import model.location.Location;
import model.location.LocationCatalog;
import model.location.SiteCatalog;
import model.person.Person;
import model.person.PersonDirectory;

public class DataLoader {
    private Faker faker;
    private Random random;
    private PersonDirectory personDirectory;
    private LocationCatalog locationCatalog;
    private ClinicCatalog clinicCatalog;
    private SiteCatalog siteCatalog;
    private int DOCTOR_COUNT = 2;
    private int NURSE_COUNT = 2;
    private int PATIENT_COUNT = 10;
    private int SITE_COUNT = 2;
    private int NON_PATIENT_COUNT = 30;

    public DataLoader(String locale) {
        faker = new Faker(new Locale(locale));
        random = new Random();
        personDirectory = new PersonDirectory();
    }


    public ArrayList<String> getClinic() {
        ArrayList<String> clinics = new ArrayList<>();
        clinics.add("CVS");
        clinics.add("DPH");
        return clinics;
    }

    public ArrayList<String> getCities(int count) {
        ArrayList<String> cities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cities.add(faker.address().cityName());
        }
        return cities;
    }

    public ArrayList<String> getNames(int count) {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            names.add(faker.name().fullName());
        }
        return names;
    }


    // creat 10 patients & two doctors & two nurses for each clinic
    public ClinicCatalog generateClinicCatalog(ArrayList<String> clinics) {
        clinicCatalog = new ClinicCatalog();

        for (String clinicName : clinics) {
            Clinic clinic = clinicCatalog.newClinic(clinicName);
            for (int i = 0; i < DOCTOR_COUNT; i++) {
                clinic.getEmployeeDirectory().newEmployee(
                        personDirectory.newPerson(faker.name().firstName(), dateToCalendar(faker.date().birthday())), EmployeeRole.Doctor
                );
            }

            for (int i = 0; i < NURSE_COUNT; i++) {
                clinic.getEmployeeDirectory().newEmployee(
                        personDirectory.newPerson(faker.name().firstName(), dateToCalendar(faker.date().birthday())), EmployeeRole.Nurse
                );
            }

            for (int i = 0; i < PATIENT_COUNT; i++) {
                clinic.getPatientDirectory().newPatient(personDirectory.newPerson(faker.name().firstName(), dateToCalendar(faker.date().birthday())));
            }
        }
        return clinicCatalog;
    }


    public LocationCatalog generateLocationCatalog(ArrayList<String> cities) {
        locationCatalog = new LocationCatalog();
        for (String city : cities) {
            locationCatalog.newLocation(city);
        }
        return locationCatalog;
    }

    public SiteCatalog generateSiteCatalog() {
        siteCatalog = new SiteCatalog();
        for (Location location : locationCatalog.getLocations()) {
            for (int i = 0; i < SITE_COUNT; i++) {
                siteCatalog.newSite("Site" + i, location, clinicCatalog.getClinics().get(i));
            }
        }
        return siteCatalog;
    }

    public Campaign generateCampaign(String name, ClinicCatalog clinicCatalog, LocationCatalog locationCatalog, SiteCatalog siteCatalog) {
        return new Campaign(name, clinicCatalog, locationCatalog, siteCatalog);
    }

    public ArrayList<String> generateEventNames() {
        ArrayList<String> eventNames = new ArrayList<>();
        for (int i = 0; i < siteCatalog.getSites().size(); i++) {
            eventNames.add(faker.dune().planet());
        }
        return eventNames;
    }

    public void addNonPatients() {
        for (int i = 0; i < NON_PATIENT_COUNT; i++) {
            personDirectory.newPerson(faker.name().firstName(), dateToCalendar(faker.date().birthday()));
        }
    }

    //random.nextInt -- uniformly distributed int value between 0 (inclusive) and the specified value (exclusive);
    //0-5 -- getEvents().size() = 6
    public Event getRandomEvent(Campaign campaign) {
        int idx = random.nextInt(campaign.getEventCatalog().getEvents().size());
        return campaign.getEventCatalog().getEvents().get(idx);
    }

    //exclude patients from other clinic and employees
    //find the non-patient & this event's clinics patient
    public Person getRandomPerson(Event event) {
        HashSet<Person> nonClinicEmployeePersons = new HashSet<>();  //remove duplicate
        boolean isPatient = false;
        for (Person person : personDirectory.getPersons()) {
            for (Clinic clinic : clinicCatalog.getClinics()) {    //two clinics
                if (!clinic.getEmployeeDirectory().isEmployee(person)  //person not employees & is this clinics 's patient
                        && clinic.getPatientDirectory().findPatient(person) != null) {
                    isPatient = true;
                    if (event.getSite().getClinic().equals(clinic) ) {
                        nonClinicEmployeePersons.add(person);
                    }
                }
            }

            if (!isPatient) {
                nonClinicEmployeePersons.add(person);
            }
        }

        int idx = random.nextInt(nonClinicEmployeePersons.size());
        ArrayList<Person> result = new ArrayList<>();
        result.addAll(nonClinicEmployeePersons);  //dump all persons into Arraylist
        return result.get(idx); //get a random person
    }

    public Employee getRandomNurse(Event event) {
        int idx = random.nextInt(event.getSite().getClinic().getEmployeeDirectory().getNurses().size());
        return event.getSite().getClinic().getEmployeeDirectory().getNurses().get(idx);
    }

    public Employee getRandomDoctor(Event event) {
        int idx = random.nextInt(event.getSite().getClinic().getEmployeeDirectory().getDoctors().size());
        return event.getSite().getClinic().getEmployeeDirectory().getDoctors().get(idx);
    }

    public ArrayList<String> generateChefComplaints(int count) {
        ArrayList<String> complaints = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            complaints.add(faker.dune().quote());
        }
        return complaints;
    }

    public ArrayList<int[]> generateVitalSigns(int count) {
        ArrayList<int[]> vitalSigns = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            vitalSigns.add(new int[] {
                    faker.number().numberBetween(80, 180),
                    faker.number().numberBetween(130, 200),
                    faker.number().numberBetween(90, 110)
            });
        }
        return vitalSigns;
    }

    public ArrayList<double[]> generateBloodSamples(int count) {
        ArrayList<double[]> bloodSamples = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bloodSamples.add(new double[]{
                    faker.number().randomDouble(1, 3, 10),
                    faker.number().randomDouble(1, 3, 10),
                    faker.number().randomDouble(1, 3, 10)
            });
        }
        return bloodSamples;
    }

    public ArrayList<Person> generateCloseContactPersons(Event event) {
        ArrayList<Person> closeContacts = new ArrayList<>();

        // random select a close contact count
        for (int j = 0; j < random.nextInt(5); j++) {

            // there has some chance(1/10) the close contact is a completely a new person
            // iterate over each candidate
            int dice = random.nextInt(10);
            if (dice == 0) {
                Person person = personDirectory.newPerson(faker.name().firstName(), dateToCalendar(faker.date().birthday()));
                closeContacts.add(person);
            } else {
                closeContacts.add(getRandomPerson(event));
            }
        }
        return closeContacts;
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
