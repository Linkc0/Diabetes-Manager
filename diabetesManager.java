import java.util.Scanner;

// Record holding information for patients
class Patients {
    final int MAX_PATIENTS = 300;

    String[] surnames = new String[MAX_PATIENTS];
    String[] hasDiabetes = new String[MAX_PATIENTS];
    int[] improvementRegimes = new int[MAX_PATIENTS];

    int patientCounter = 0;

} // END class Patients

class DiabetesManager {

    // Main method where other methods will be called
    public static void main(String[] args) {
        Patients newPatients = new Patients();
        inputPatientNames(newPatients);
        menu(newPatients);
    } // END main

    // Set and get methods for surnames
    public static String getSurname(Patients newPatients, int index) {
        return newPatients.surnames[index];
    } // END getSurname


    public static String getHasDiabetes(Patients newPatients, int index) {
        return newPatients.hasDiabetes[index];
    } // END getHasDiabetes


    public static int getImprovementRegime(Patients newPatients, int index) {
        return newPatients.improvementRegimes[index];
    } // END getImprovementRegime


    public static int getPatientCounter(Patients newPatients) {
        return newPatients.patientCounter;
    } // END getPatientCounter


    public static int getMaxPatients(Patients newPatients) {
        return newPatients.MAX_PATIENTS;
    } // END getMaxPatients


    public static void setSurname(Patients newPatients, int index, String surname) {
        newPatients.surnames[index] = surname;
    } // END setSurname


    public static void setHasDiabetes(Patients newPatients, int index, String hasDiabetes) {
        newPatients.hasDiabetes[index] = hasDiabetes;
    } // END setHasDiabetes


    public static void setImprovementRegime(Patients newPatients, int index, int regime) {
        newPatients.improvementRegimes[index] = regime;
    } // END setImprovementRegime


    public static void setPatientCounter(Patients newPatients, int counter) {
        newPatients.patientCounter = counter;
    } // END setPatientCounter


    // Handles user inputs
    public static String inputs(String message) {
        String userInput;
        final Scanner scanner = new Scanner(System.in);
        print(message);
        userInput = scanner.nextLine();
        return userInput;
    } // END inputs

    // Shorter outputting method
    public static void print(String message) {
        System.out.println(message);
    } // END print


    // Adds a patient with details: surname, diabetes status, and improvement regime
    public static void addPatientDetails(Patients newPatients) {
        int counter = getPatientCounter(newPatients);
        int maxPatients = getMaxPatients(newPatients);
        String surname;

        if (counter < maxPatients) {
            surname = inputs("Enter the patient's surname: ");

            if (checkIfPatientExists(newPatients, surname)) {
                print("A patient with this surname already exists.");
            } else {
                setSurname(newPatients, counter, surname);
                updateDiabetesStatus(newPatients, counter);
                updateImprovementRegime(newPatients, counter);

                setPatientCounter(newPatients, counter + 1);
            }
        } else {
            print("Maximum number of patients recorded.");
        }
    }
    //END addPatientDetails


    // Updates diabetes status of a patient
    public static void updateDiabetesStatus(Patients newPatients, int index) {
        String diabetesStatus = inputs("Does the patient have diabetes? (yes/no): ");

        while (!isValidYesNo(diabetesStatus)) {
            diabetesStatus = inputs("Invalid input. Please enter 'yes' or 'no': ");
        }
        if (diabetesStatus.equals("yes")){
            setHasDiabetes(newPatients, index, "Positive");
        } else{
            setHasDiabetes(newPatients, index, "Negative");
        }

        return;
    } // END updateDiabetesStatus

    // Updates improvement regime of a patient
    public static void updateImprovementRegime(Patients newPatients, int index) {
        String regime = inputs("Enter improvement regime (1: diet, 2: exercise, 3: drugs): ");

        while (!isValidRegime(regime)) {
            regime = inputs("Invalid input. Please enter '1', '2', or '3': ");
        }
        setImprovementRegime(newPatients, index, Integer.parseInt(regime));

        return;
    } // END updateImprovementRegime


    /// Updates patient details
    public static void updatePatientDetails(Patients newPatients) {
        String name = inputs("Enter the surname of the patient to update: ");
        int numOfRecordedPatients = getPatientCounter(newPatients);
        boolean found = false;

        for (int patientIndex = 0; patientIndex < numOfRecordedPatients; patientIndex++) {
            if (getSurname(newPatients, patientIndex).equals(name)) {
                updateDiabetesStatus(newPatients, patientIndex);
                updateImprovementRegime(newPatients, patientIndex);
                found = true;
            }
        }
        if (!found) {
            print("Patient not found.");
        }
    } // END updatePatientDetails


    // Prints all patient details
    public static void printAllPatients(Patients newPatients) {
        int counter = getPatientCounter(newPatients);
        String diabetesStatus;
        String regime;


        if (counter == 0) {
            print("No patients recorded.");
            }else {
            for (int patientIndex = 0; patientIndex < counter; patientIndex++) {
                diabetesStatus = stringDiabetesStatus(newPatients, patientIndex);
                regime = stringRegimeName(newPatients, patientIndex);

                print((patientIndex + 1) + ". Surname: " + getSurname(newPatients, patientIndex) + ", Diabetes status: " + diabetesStatus + ", Regime: " + regime);
            }
        }
    } // END printAllPatients


    // Prints details of a patient's index by surname
    public static void findPatientIndex(Patients newPatients, String surname) {
        int numOfRecordedPatients = getPatientCounter(newPatients);
        boolean found = false;

        for (int patientIndex = 0; patientIndex < numOfRecordedPatients; patientIndex++) {
            if (getSurname(newPatients, patientIndex).equals(surname)) {
                found = true;
                print("Patient found: " + getSurname(newPatients, patientIndex));
                print("Diabetes status: " + stringDiabetesStatus(newPatients, patientIndex));
                print("Improvement regime: " + stringRegimeName(newPatients, patientIndex));
            }
        }

        if (!found) {
            print("No patient found with surname: " + surname);
        }
    } // END findPatientIndex



    // Checks if user input is a valid "yes" or "no"
    public static boolean isValidYesNo(String input) {
        return input.equals("yes") || input.equals("no");
    } // END isValidYesNo

    // Checks if the regime input is valid
    public static boolean isValidRegime(String input) {
        return input.matches("[1-3]");
    } // END isValidRegime

    // Retrieves the diabetes status message for a patient based on the stored status
    public static String stringDiabetesStatus(Patients newPatients, int patientIndex) {
        String diabetesStatus = getHasDiabetes(newPatients, patientIndex);

        if (diabetesStatus != null && (diabetesStatus.equals("Positive") || diabetesStatus.equals("Negative"))) {
            return diabetesStatus;
        } else {
            return "Unknown";
        }
    } // END stringDiabetesStatus



    // Retrieves the name of the regime based on its number
    public static String stringRegimeName(Patients newPatients, int patientIndex) {
        int regime = getImprovementRegime(newPatients, patientIndex);

        if (regime == 1) {
            return "Diet";
        } else if (regime == 2) {
            return "Exercise";
        } else if (regime ==3 ) {
            return "Drugs";
        }else{
            return "Unknown";
        }
    } // END getRegimeName


    // Input all patients until XXX entered or reach maximum
    public static void inputPatientNames(Patients newPatients) {
        final String END = "XXX";
        int count = 0;
        String surname = inputs("Please enter each active patient's surname: ");

        while (!(surname.equals(END)) && (count < getMaxPatients(newPatients))) {
            setSurname(newPatients, count, surname);
            setPatientCounter(newPatients, count + 1);
            count = getPatientCounter(newPatients);

            surname = inputs("Please enter next surname (or enter XXX to finish)");
        }
    } // END inputPatientNames

    // Checks if a patient with the given surname already exists
    public static boolean checkIfPatientExists(Patients newPatients, String surname) {
        int numOfRecordedPatients = getPatientCounter(newPatients);

        for (int patientIndex = 0; patientIndex < numOfRecordedPatients; patientIndex++) {
            if (getSurname(newPatients, patientIndex).equals(surname)) {
                return true;
            }
        }

        return false;
    }//END checkIfPatientExists

    // Main menu loop
    public static void menu(Patients newPatients) {
        boolean exit = false;

        while (!exit) {
            String input = inputs("Enter: (E)xit, (I)nput further patient details, (P)rint all patient details, (A)dd details for a new patient, (F)ind specific patient status").toUpperCase();

            if (input.equals("E")) {
                exit = true;
            } else if (input.equals("I")) {
                updatePatientDetails(newPatients);
            } else if (input.equals("P")) {
                printAllPatients(newPatients);
            } else if (input.equals("A")) {
                addPatientDetails(newPatients);
            } else if (input.equals("F")) {
                String surname = inputs("Enter the surname of the patient to find: ");
                findPatientIndex(newPatients, surname);
            } else {
                print("Invalid input. Please enter 'E', 'I', 'P', 'A', or 'F'.");
            }
        }
    }
    // END menu

} // END class DiabetesManager
