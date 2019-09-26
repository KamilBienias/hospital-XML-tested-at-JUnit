import java.util.GregorianCalendar;
import java.util.Objects;

abstract public class Person implements Comparable<Person>{

    private String firstName;
    private String lastName;
    private GregorianCalendar dateOfBirth;

    //a non-argument constructor is needed to create an xml file
    public Person() {
    }

    public Person(String firstName, String lastName, GregorianCalendar dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    //getters are needed to xml file
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(GregorianCalendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public String toString() {
        String date = "not set";
        if (dateOfBirth!=null) {
            date = dateOfBirth.get(GregorianCalendar.DAY_OF_MONTH) + "." +
                    (dateOfBirth.get(GregorianCalendar.MONTH) + 1) + "." + dateOfBirth.get(GregorianCalendar.YEAR);
        }
        return  "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + date;
    }

    @Override
    public int compareTo(Person o) {
        return this.lastName.compareTo(o.lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(dateOfBirth, person.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth);
    }
}