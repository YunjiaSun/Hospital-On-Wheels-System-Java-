package model.person;

import java.util.ArrayList;
import java.util.Calendar;

import model.location.Location;

public class Person {
    private String name;
    private Calendar dateOfBirth;
    private PersonDirectory personDirectory;

    public Person(PersonDirectory pd, String n, Calendar dob) {
        personDirectory = pd;
        name = n;
        dateOfBirth = dob;
    }

    public String getName() {
        return name;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public String toString() {
        return "Name: " + name + " | DOB: " + dateOfBirth.get(Calendar.YEAR) + "-" +
                (dateOfBirth.get(Calendar.MONTH) + 1) + "-" +
                dateOfBirth.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (dateOfBirth == null ? 0 : dateOfBirth.hashCode());
        return hash;
    }

    // use for hashmap to identify if this person is equal to other
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name)
                && dateOfBirth.equals(person.dateOfBirth);
    }
}
