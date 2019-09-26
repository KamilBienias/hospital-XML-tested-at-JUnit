import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    private Database database = new Database();

    UserInterface UI;

    @BeforeEach
    void setUp() {
        //an example of database
        List<Doctor> doctors = new ArrayList<>();

        doctors.add(new Doctor("Jan", "Kowalski", new GregorianCalendar(1945,4,15), 4300, 50.5,null));
        doctors.add(new Doctor("Kamil", "Dereszewski", new GregorianCalendar(2012,10,23), 578, 45.6,null));
        doctors.add(new Doctor("Adam", "Zdanowicz", new GregorianCalendar(19468,2,21), 8900, 56,null));

        database.getDoctors().addAll(doctors);

        UI = new UserInterface(database);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Displays after each test-method");
    }

    @Test
    void shouldIncreasePaymentForDoctorsBy10percent(){
        //when
        ArrayList<Doctor> doctorsAfterIncrease = (ArrayList<Doctor>) UI.increasePaymentForDoctors(10);

        //then
        assertEquals(4730, doctorsAfterIncrease.get(0).getSalary(), 0.01);
        assertEquals( 635.8, doctorsAfterIncrease.get(1).getSalary(), 0.01);
        assertEquals(9790, doctorsAfterIncrease.get(2).getSalary(), 0.01);

    }

}