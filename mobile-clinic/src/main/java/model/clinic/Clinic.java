package model.clinic;

import model.location.SiteCatalog;
import model.employee.EmployeeDirectory;
import model.patient.PatientDirectory;

public class Clinic {
    String name;
    EmployeeDirectory employeeDirectory;
    PatientDirectory patientDirectory;

    public Clinic(String n) {
        name = n;
        patientDirectory = new PatientDirectory(this);
        employeeDirectory = new EmployeeDirectory(this);
    }

    public String getName() {
        return name;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }

    public PatientDirectory getPatientDirectory() {
        return patientDirectory;
    }


    public String toString() {
        return "Clinic name: " + name + "\n" +
                "Employees: \n" + employeeDirectory.toString() + "\n" +
                "Patients: \n" + patientDirectory.toString() + "\n";
    }
}
