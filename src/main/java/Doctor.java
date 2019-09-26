import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class Doctor extends Person {

    private double salary;
    private double bonus;
    private List<Patient> patientsOfThisDoctor;

    //a non-argument constructor is needed to create an xml file
    public Doctor(){}

    //this constructor is needed to tests
    public Doctor(String firstName, String lastName, GregorianCalendar dateOfBirth, double salary, double bonus, List<Patient> patientsOfThisDoctor) {
        super(firstName, lastName, dateOfBirth);
        this.salary = salary;
        this.bonus = bonus;
        this.patientsOfThisDoctor = patientsOfThisDoctor;
    }

    //getters are needed to xml file
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public List<Patient> getPatientsOfThisDoctor() {
        return patientsOfThisDoctor;
    }

    public void setPatientsOfThisDoctor(List<Patient> patientsOfThisDoctor) {
        this.patientsOfThisDoctor = patientsOfThisDoctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Double.compare(doctor.salary, salary) == 0 &&
                Double.compare(doctor.bonus, bonus) == 0 &&
                Objects.equals(patientsOfThisDoctor, doctor.patientsOfThisDoctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salary, bonus, patientsOfThisDoctor);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", patients of this doctor=" + patientsOfThisDoctor;
    }
}