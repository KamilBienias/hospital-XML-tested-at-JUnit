import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.YearMonth;
import java.util.*;

public class UserInterface {

    private Database database;

    UserInterface(Database database) {
        this.database = database;
    }

    private void menu(){
        boolean quit = false;
        while(!quit) {
            System.out.println("\nMenu for all users:");
            System.out.println("1. Register for the first time as a patient");
            System.out.println("2. Display all doctors alphabetically by surname");
            System.out.println("3. Display doctors who do not have patients");
            System.out.println("4. Log in as a director");
            System.out.println("0. Quit");

            System.out.print("Please, enter the command number: ");
            Scanner scanner = new Scanner(System.in);
            boolean isThereError = true;
            int commandNumber = -1;
            while (isThereError) {
                try {
                    commandNumber = scanner.nextInt();
                    if (commandNumber > 5 || commandNumber < 0) {// checks whether the integer number is from the menu, if not then throws IllegalArgumentException
                        throw new IllegalArgumentException();
                    }
                    isThereError = false;// when there is no exception, it leaves the try-catch block
                } catch (InputMismatchException letter) {
                    System.err.print("It is not an integer. Please, enter the command number: ");
                    scanner.nextLine();
                } catch (IllegalArgumentException outOfMenu) {
                    System.err.printf("Number %d is out of menu. ", commandNumber);
                    System.out.print("Please, enter the command number: ");
                    scanner.nextLine();
                }
            }

            switch (commandNumber) {
                case 1:
                    System.out.println("\nYou have chosen to register as a patient");
                    addPatient();
                    break;

                case 2:
                    System.out.println("\nYou have chosen to display all doctors alphabetically by surname");
                    displayAllDoctorsAlphabetically();
                    break;

                case 3:
                    System.out.println("\nYou have chosen to display doctors without patients");
                    displayDoctorsWhoDontHavePatients();
                    break;

                case 4:
                    System.out.println("\nYou have chosen to log in as a director");
                    loginAsDirector();
                    break;

                case 0:
                    System.out.println("\nYou have chosen to quit");
                    quit = true;
            }
        }
    }

    /**
     * Introduces the first and last name and date of birth of the person
     * @param person The parameter is a new person, such as a doctor or patient
     *
     */
    private void enterPersonalInformation(Person person){
        Scanner scanner = new Scanner(System.in);

        System.out.print("First name: ");
        String name = scanner.nextLine();

        System.out.print("Last name: ");
        String surname = scanner.nextLine();

        GregorianCalendar today = new GregorianCalendar();
        int thatYear = today.get(GregorianCalendar.YEAR);

        System.out.print("The year of birth: ");
        int year = 0;
        boolean isYearError = true;
        while (isYearError) {
            try {
                year = scanner.nextInt();
                if(year < 1900 || year > thatYear){
                    throw new IllegalArgumentException();
                }
                isYearError = false;
            } catch (InputMismatchException letter) {
                System.out.print("It is not a year. The year of birth: ");
                scanner.nextLine();
            } catch (IllegalArgumentException wrongYear){
                System.out.printf("Year of birth %d is impossible. ", year);
                System.out.print("Please, enter the year of birth: ");
                scanner.nextLine();
            }
        }

        System.out.print("The month of birth: ");
        boolean isMonthError = true;
        int month = 0;
        while (isMonthError){
            try{
                month = scanner.nextInt();
                if(month < 1 || month > 12){
                    throw new IllegalArgumentException();
                }
                isMonthError = false;
            }catch (InputMismatchException letter){
                System.out.print("It is not a month. The month of birth: ");
                scanner.nextLine();
            }catch (IllegalArgumentException wrongMonth){
                System.out.printf("Month of birth %d is impossible. ", month);
                System.out.print("Please, enter the month of birth: ");
                scanner.nextLine();
            }
        }

        YearMonth date = YearMonth.of(year, month);// new object, which is the date of the year and month number
        int daysInThatMonth = date.lengthOfMonth();

        System.out.print("The day of birth: ");
        boolean isDayError = true;
        int day = 0;
        while (isDayError){
            try{
                day = scanner.nextInt();
                if(day < 1 || day > daysInThatMonth){
                    throw new IllegalArgumentException();
                }
                isDayError = false;
            }catch (InputMismatchException letter){
                System.out.print("It is not a day. The day of birth: ");
                scanner.nextLine();
            }catch (IllegalArgumentException wrongDay) {
                System.out.printf("Day of birth %d is impossible. ", day);
                System.out.print("Please, enter the day of birth: ");
                scanner.nextLine();
            }
        }

        person.setFirstName(name);
        person.setLastName(surname);
        person.setDateOfBirth(new GregorianCalendar(year, month - 1, day));
    }

    // menu 1.
    private void addPatient(){
        Patient newPatient = new Patient();
        enterPersonalInformation(newPatient);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your disease: ");
        String disease = scanner.nextLine();
        newPatient.setDisease(disease);

        displayAllDoctorsAlphabetically();

        int numberOfMyDoctor;
        try {
            System.out.print("Choose your doctor from the list above or press any key if it is empty list: ");
            numberOfMyDoctor = scanner.nextInt();
            database.getDoctors().get(numberOfMyDoctor - 1).getPatientsOfThisDoctor().add(newPatient);
            database.getPatients().add(newPatient);
            System.out.println("Patient is added to database: ");
            System.out.println(newPatient);
        } catch (Exception e) {
            System.out.println("We can not add this patient because of lack of doctors");
        }
    }

    // menu 2.
    private void displayAllDoctorsAlphabetically() {
        try {
            List<Doctor> doctorsSorted = new ArrayList<>(database.getDoctors());
            Collections.sort(doctorsSorted);//sorts alphabetically by lastName using the compateTo method in Person class
            for (int i = 0; i < doctorsSorted.size(); i++){
                System.out.println(i+1 + ". Surname: " + doctorsSorted.get(i).getLastName() + ", first name: " + doctorsSorted.get(i).getFirstName());//mozna doctorsSorted.get(i).toString()
            }
        }catch (Exception e){
            System.out.println("There are no doctors in our hospital so we can not display their alphabetically");
        }
    }

    // menu 3.
    private void displayDoctorsWhoDontHavePatients(){
        try {
            Collections.sort(database.getDoctors());
            for (int i = 0; i < database.getDoctors().size(); i++){
                if(database.getDoctors().get(i).getPatientsOfThisDoctor().isEmpty()){
                    System.out.println("Surname: " + database.getDoctors().get(i).getLastName() + ", first name: " + database.getDoctors().get(i).getFirstName());
                }
            }
        } catch (Exception e){
            System.out.println("There are no doctors in our hospital so they all do not have patients");
        }
    }

    // menu 4.
    private void loginAsDirector() {

        Scanner scanner = new Scanner(System.in);
        String password;
        do {
            System.out.print("Enter the password (prompt: director1234): ");
            password = scanner.nextLine();
        } while (!password.equals("director1234"));
        System.out.println("\nCorrect password.");
        menuDirector();
    }

    private void menuDirector(){
        boolean quit = false;
        while(!quit) {
            System.out.println("\nMenu for the director:");
            System.out.println("1. Add a doctor");
            System.out.println("2. Remove a doctor");
            System.out.println("3. Increase the payment for all doctors");
            System.out.println("4. Decrease the payment for all doctors");
            System.out.println("5. Set bonus for selected doctors");
            System.out.println("6. Display all doctors");
            System.out.println("7. Display all patients alphabetically");
            System.out.println("8. Display the patients whose surname begins with the given letters");
            System.out.println("9. Display doctors according to their salary ascending");
            System.out.println("0. Quit");

            System.out.print("Please, enter the command number: ");
            Scanner in = new Scanner(System.in);
            boolean isThereError = true;
            int commandNumber = -1;
            while (isThereError) {
                try {
                    commandNumber = in.nextInt();
                    if (commandNumber > 9 || commandNumber < 0) {// // checks whether the total number is from the director menu, if not then throws IllegalArgumentException
                        throw new IllegalArgumentException();
                    }
                    isThereError = false;// when there is no exception, it leaves the try-catch block
                } catch (InputMismatchException letter) {
                    System.err.print("It is not an integer. Please, enter the command number: ");
                    in.nextLine();
                } catch (IllegalArgumentException outOfMenu) {
                    System.err.printf("Number %d is out of menu. ", commandNumber);
                    System.out.print("Please, enter the command number: ");
                    in.nextLine();
                }
            }

            switch (commandNumber) {
                case 1:
                    System.out.println("\nYou have chosen to add a doctor");
                    addDoctor();
                    displayAllDoctors();
                    break;

                case 2:
                    System.out.println("\nYou have chosen to remove a doctor");
                    removeDoctor();
                    break;

                case 3:
                    System.out.println("\nYou have chosen to increase payment for all doctors");
                    increasePaymentForDoctors();
                    break;

                case 4:
                    System.out.println("\nYou have chosen to decrease payment for all doctors");
                    decreasePaymentForDoctors();
                    break;

                case 5:
                    System.out.println("\nYou have chosen to set bonus for doctors");
                    setBonusForSelectedDoctors();
                    break;

                case 6:
                    System.out.println("\nYou have chosen to display all doctors");
                    displayAllDoctors();
                    break;

                case 7:
                    System.out.println("\nYou have chosen to display all patients alphabetically by surname");
                    displayAllPatientsAlphabetically();
                    break;

                case 8:
                    System.out.println("You have chosen to display the patients whose surname begins with the given letter");
                    displayPatientsWhoseSurnameBeginsWithGivenLetters();
                    break;

                case 9:
                    System.out.println("You have chosen to display doctors according to their salary ascending");
                    displayDoctorsSalaryAscending();
                    break;

                case 0:
                    System.out.println("\nYou have chosen to quit from director menu");
                    quit = true;
            }
        }
    }


    // menu director 1.
    private void addDoctor() {
        Doctor newDoctor = new Doctor();
        enterPersonalInformation(newDoctor);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Set salary: ");
        double salary = scanner.nextDouble();
        newDoctor.setSalary(salary);
        System.out.print("Set bonus: ");
        double bonus = scanner.nextDouble();
        newDoctor.setBonus(bonus);
        newDoctor.setPatientsOfThisDoctor(new ArrayList<>());//in case of NullPointerException
        //check if the doctor already exists, and if he does not add it to the database
        if(!database.getDoctors().contains(newDoctor)){
            database.getDoctors().add(newDoctor);
            System.out.println("Doctor is added to database:\n " + newDoctor);
        }else {
            System.out.println("This doctor is already in the database. Enter a new doctor");
            addDoctor();
        }

    }

    // menu director 2.
    private void removeDoctor() {
        displayAllDoctors();

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose a doctor to remove from the list above or press any key if list is empty: ");
            //====================== zrobic obsluge wyjatkow dla wprowadzania numeru doktora ================================
            int numberOfRemovedDoctor = scanner.nextInt();
            System.out.println("Doctor " + database.getDoctors().get(numberOfRemovedDoctor - 1));
            System.out.println("is removed from the list of doctors");
            //usuwam lekarza
            database.getDoctors().remove(numberOfRemovedDoctor - 1);
        } catch (Exception e) {
            System.out.println("We can not remove a doctor if there are no doctors");
        }
    }

    // menu director 3.
    private void increasePaymentForDoctors() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Doctors before salary increase:");
            for (Doctor doctor : database.getDoctors()) {
                System.out.println(doctor);
            }

            System.out.println();
            System.out.print("By how many percent increase the salary of doctors? ");
            double percentOfIncrease = scanner.nextDouble();

            for (Doctor doctor : database.getDoctors()) {
                doctor.setSalary(doctor.getSalary() * (1 + percentOfIncrease/100.0));
            }

            System.out.println();
            System.out.printf("Doctors after a salary increase of %s percent", percentOfIncrease);
            System.out.println();
            for (Doctor doctor : database.getDoctors()) {
                System.out.println(doctor);
            }
        } catch (Exception e) {
            System.out.println("There are no doctors so we can not increase salary");
        }
    }

    // Overloaded method. Made for testing purposes.
    public List<Doctor> increasePaymentForDoctors(double percentOfIncrease) {
        try {
            System.out.println("Doctors before salary increase:");
            for (Doctor doctor : database.getDoctors()) {
                System.out.println(doctor);
            }


            for (Doctor doctor : database.getDoctors()) {
                doctor.setSalary(doctor.getSalary() * (1 + percentOfIncrease/100.0));
            }

            System.out.println();
            System.out.printf("Doctors after a salary increase of %s percent", percentOfIncrease);
            System.out.println();
            for (Doctor doctor : database.getDoctors()) {
                System.out.println(doctor);
            }
        } catch (Exception e) {
            System.out.println("There are no doctors so we can not increase salary");
        }

        return database.getDoctors();
    }

    // menu director 4.
    private void decreasePaymentForDoctors() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Doctors before salary decrease:");
            for (Doctor doctor : database.getDoctors()) {
                System.out.println(doctor);
            }

            System.out.println();
            System.out.print("By how many percent decrease the salary of doctors? ");
            double percentOfDecrease = scanner.nextDouble();

            for (Doctor doctor : database.getDoctors()) {
                doctor.setSalary(doctor.getSalary() * (1 - percentOfDecrease/100.0));
            }

            System.out.println();
            System.out.printf("Doctors after a salary decrease of %s percent", percentOfDecrease);
            System.out.println();
            for (Doctor doctor : database.getDoctors()) {
                System.out.println(doctor);
            }
        } catch (Exception e) {
            System.out.println("There are no doctors so we can not decrease salary");
        }
    }

    // menu director 5.
    private void setBonusForSelectedDoctors() {
        displayAllDoctors();
        Scanner scanner = new Scanner(System.in);
        int numberOfMyDoctor;
        try {
            System.out.print("Choose doctor from the list above or press any key if it is empty list: ");
            numberOfMyDoctor = scanner.nextInt();
            System.out.println("Set bonus for doctor number " + numberOfMyDoctor);
            double bonus = scanner.nextDouble();
            //przypisuje bonus do wybranego lekarza
            database.getDoctors().get(numberOfMyDoctor - 1).setBonus(bonus);
        } catch (Exception e) {
            System.out.println("We can not set bonus because of lack of doctors");
        }
    }

    // menu director 6.
    private void displayAllDoctors() {
        try {
            System.out.println("List of all doctors:");
            for (int i = 0; i < database.getDoctors().size(); i++){
                System.out.println(i+1 + ". " + database.getDoctors().get(i));
            }
        } catch (Exception e){
            System.out.println("There are no doctors in our hospital");
        }
    }

    // menu director 7.
    private void displayAllPatientsAlphabetically() {
        try {
            System.out.println("List of all patients:");
            database.getPatients()
                    .stream()
                    .sorted(Comparator.comparing(Person::getLastName))//if case is irrelevant then .sorted((patient1, patient2) -> patient1.getLastName().compareToIgnoreCase(patient2.getLastName()))
                    .forEach(patient -> System.out.println(patient));//insted of .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("There are no patients in our hospital");
        }
    }

    // menu director 8.
    private void displayPatientsWhoseSurnameBeginsWithGivenLetters() {
        System.out.print("Write the first letters of the surname (enter the first capital letter): ");
        Scanner scanner = new Scanner(System.in);
        String firstLettersOfSurname = scanner.nextLine();
        try {
            database.getPatients()
                    .stream()
                    .filter(patient -> patient.getLastName().startsWith(firstLettersOfSurname))
                    .forEach(patient -> System.out.println(patient));//insted of .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("There are no patients in our hospital");
        }
    }

    // menu director 9.
    private void displayDoctorsSalaryAscending() {
        try {
            database.getDoctors()
                    .stream()
                    .sorted(Comparator.comparing(Doctor::getSalary))
                    .forEach(doctor -> System.out.println(doctor));//insted of .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("There are no doctors in our hospital");
        }
    }

    public static void main(String[] args) throws JAXBException {

        Database database = new Database();

        try {
            Unmarshaller unmarshaller = UnmarshallerExample.generateUnmarshaller();

            database = (Database) unmarshaller.unmarshal(new File("database.xml"));
        } catch (JAXBException e) {
            System.out.println("\nCreates new database");
        }

        UserInterface UI = new UserInterface(database);

        UI.menu();

        Marshaller marshaller = MarshallerExample.generateMarshaller();

        marshaller.marshal(database, new File("database.xml"));

    }
}