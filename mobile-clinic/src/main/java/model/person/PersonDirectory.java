package model.person;

import java.util.ArrayList;
import java.util.Calendar;


public class PersonDirectory {
    private ArrayList<Person> persons;

    public PersonDirectory() {
        persons = new ArrayList<>();
    }

    public Person newPerson(String name, Calendar dateOfBirth) {
        Person p = new Person(this, name, dateOfBirth);
        persons.add(p);
        return p;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Person p : persons) {
            sb.append(p + "\n");
        }
        return sb.toString();
    }
}
