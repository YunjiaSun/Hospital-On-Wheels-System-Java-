package model.employee;

import model.person.Person;

public class Employee {
    private Person person;
    private EmployeeDirectory employeeDirectory;
    private EmployeeRole role;

    public Employee(Person p, EmployeeDirectory e, EmployeeRole r){
        person = p;
        employeeDirectory = e;
        role = r;
    }

    public Person getPerson() {
        return person;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public String toString() {
        return this.role + ": " + this.person;
    }
    
}
