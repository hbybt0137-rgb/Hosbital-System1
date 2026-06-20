import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    private static final String DOCTORS_FILE = "doctors.txt";
    private static final String PATIENTS_FILE = "patients.txt";
    private static final String APPOINTMENTS_FILE = "appointments.txt";
    private static final String USERS_FILE = "users.txt";


    
    public static void saveDoctors(ArrayList<Doctor> doctors) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DOCTORS_FILE))) {
            for (Doctor d : doctors) {
                writer.println(d.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors: " + e.getMessage());
        }
    }


    public static void savePatients(ArrayList<Patient> patients) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PATIENTS_FILE))) {
            for (Patient p : patients) {
                writer.println(p.toFileString());
            }
            System.out.println("Patients saved successfully in txt file");
        } catch (IOException e) {
            System.out.println("Error saving patients: " + e.getMessage());
        }
    }

    
    public static void saveAppointments(ArrayList<Appointment> appointments) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENTS_FILE,false))) {
            for (Appointment a : appointments) {
                writer.println(a.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments: " + e.getMessage());
        }
    }


    public static void saveUsers(ArrayList<Doctor> doctors, ArrayList<Patient> patients) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            writer.println("A000,Admin,admin,admin123,Admin");
            for (Doctor d : doctors) {
                writer.println(d.getID() + "," + d.getName() + "," + d.getUserName() + "," + d.getPassword()+",Doctor");
            }
            for (Patient p : patients) {
                writer.println(p.getID() + "," + p.getName()     + "," + p.getUserName() + "," + p.getPassword()+",Patient");
            }
            System.out.println("Users saved successfully in txt file");
        }catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }


    public static void savedata(ArrayList<Doctor> doctors,ArrayList<Appointment> appointments,ArrayList<Patient> patients) {
        saveDoctors(doctors);
        savePatients(patients);
        saveAppointments(appointments);
        saveUsers(doctors, patients);
        System.out.println("All data saved successfully");
    }


     public static ArrayList<Doctor> loadDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        File file = new File(DOCTORS_FILE);

        if (!file.exists() || file.length() == 0) {
            System.out.println("doctors.txt not found or empty. Starting fresh.");
            return doctors;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Doctor d = Doctor.fromFileString(line);
                    if (d != null) {
                        doctors.add(d);
                    } 
                } 
            }
            System.out.println("Doctors loaded: " + doctors.size());
        } catch (FileNotFoundException e) {
            System.out.println("Error loading doctors: " + e.getMessage());
        }
        return doctors;
    }


    public static ArrayList<Patient> loadPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        File file = new File(PATIENTS_FILE);
 
        if (!file.exists() || file.length() == 0) {
            System.out.println("patients.txt not found or empty. Starting fresh.");
            return patients;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        Patient p = Patient.fromFileString(line);
                        if (p != null) {
                            patients.add(p);
                        }
                    } catch (Exception e) {
                        System.out.println("Skipping bad line: " + line);
                    }
                }
            }
            System.out.println("Patients loaded: " + patients.size());
        } catch (FileNotFoundException e) {
            System.out.println("Error loading patients: " + e.getMessage());
        }
        return patients;
    }
   

    public static ArrayList<Appointment> loadAppointments() {
    ArrayList<Appointment> appointments = new ArrayList<>();
    File file = new File(APPOINTMENTS_FILE);  
    
    if (!file.exists()) {
        System.out.println("appointments.txt not found");  
        return appointments; 
    }
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine(); 
            String[] data = line.split(","); 
            
            if (data.length == 6) { 
                Appointment a = new Appointment(data[0], data[1], data[2], data[3], data[4]);
                
                a.setStatus(data[5]); 
                
                appointments.add(a);  
            }
        }
        System.out.println("Appointments loaded: " + appointments.size()); 
    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + e.getMessage());  
    }
    return appointments;
}
}
    

