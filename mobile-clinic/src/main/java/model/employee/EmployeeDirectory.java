package model.employee;

import java.util.ArrayList;

import model.clinic.Clinic;
import model.person.Person;


public class EmployeeDirectory {
    private Clinic clinic;
    private ArrayList<Employee> employees;

    public EmployeeDirectory(Clinic c){
        clinic = c;
        employees = new ArrayList<>();
    }

    public Employee newEmployee(Person p,  EmployeeRole role) {
        Employee e = new Employee(p, this, role);
        employees.add(e);
        return e;
    }

    public Employee getNurse() {
        for (Employee e : employees) {
            if (e.getRole() ==  EmployeeRole.Nurse) {
                return e;
            }
        }
        return null;
    }

    public Employee getDoctor() {
        for (Employee e : employees) {
            if (e.getRole() == EmployeeRole.Doctor) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<Employee> getNurses() {
        ArrayList<Employee> nurses = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getRole() == EmployeeRole.Nurse) {
                nurses.add(e);
            }
        }
        return nurses;
    }

    public ArrayList<Employee> getDoctors() {
        ArrayList<Employee> doctors = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getRole() == EmployeeRole.Doctor) {
                doctors.add(e);
            }
        }
        return doctors;
    }

    public boolean isEmployee(Person person) {
        for (Employee e : employees) {
            if (e.getPerson().equals(person)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Employee e : employees) {
            sb.append(e + "\n");
        }
        return sb.toString();
    }
}
