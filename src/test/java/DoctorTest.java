import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        System.out.println("Creates new doctor all the time");
        //given
        doctor = new Doctor("Jan", "Kowalski", new GregorianCalendar(1945,4,15), 4300.23, 50.5,null);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Displays after each test-method");
    }

    @Test
    void getDateOfBirth() {
        //when
        GregorianCalendar dateOfBirth = doctor.getDateOfBirth();

        //then
        assertEquals(new GregorianCalendar(1945,4,15), dateOfBirth);
    }

    @Test
    void getSalary() {
        //when
        double salary = doctor.getSalary();

        //then
        assertEquals(4300.23, salary);
    }

    @Test
    void getBonus() {
        //when
        double bonus = doctor.getBonus();

        //then
        assertEquals(50.5, bonus);
    }
}