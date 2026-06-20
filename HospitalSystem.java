import java.util.ArrayList;
import java.util.Scanner;
 
public class HospitalSystem {
    Scanner input = new Scanner(System.in);
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;
    private ArrayList<Appointment> appointments;
 
    public void start() {
        System.out.println("Loading data...");
        doctors = FileManager.loadDoctors();
        patients = FileManager.loadPatients();
        appointments = FileManager.loadAppointments();
        Appointment.getAppointments().clear();
        Appointment.getAppointments().addAll(appointments);
        linkDoctorWithPatient();
        System.out.println("Welcome to Hospital Appointments Management System");
        MainMenu();
    }
 
    private void loginAdmin() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Username: ");
            String username = input.next();
            System.out.print("Enter Password: ");
            String password = input.next();
            if (username.equals("admin") && password.equals("admin123")) {
                System.out.println("Login successful! Welcome Admin");
                Admin admin = new Admin("A000", "Admin", "admin", "admin123", "Admin");
                admin.getDoctors().addAll(doctors);
                admin.getPatients().addAll(patients);
                //admin.getAppointments().addAll(appointments);
                admin.Showmenu();
                // reload بعد الـ logout — clear الأول عشان منضاعفش
                Appointment.getAppointments().clear();
                doctors      = FileManager.loadDoctors();
                patients     = FileManager.loadPatients();
                appointments = FileManager.loadAppointments();
                Appointment.getAppointments().addAll(appointments);
                linkDoctorWithPatient();
                return;
            }
            attempts--;
            if (attempts > 0) {
                System.out.println("Wrong username or password! (" + attempts + ") attempts remaining");
            }
        }
        System.out.println("Too many failed attempts, Returning to Main Menu...");
    }
 
    private void loginDoctor() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Username: ");
            String username = input.next();
            System.out.print("Enter Password: ");
            String password = input.next();
            for (Doctor d : doctors) {
                if (d.getUserName().equals(username) && d.getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome Dr. " + d.getName());
                    d.Showmenu();
                    return;
                }
            }
            attempts--;
            if (attempts > 0) {
                System.out.println("Wrong username or password! (" + attempts + ") attempts remaining");
            }
        }
        System.out.println("Too many failed attempts, Returning to Main Menu...");
    }
 
    private void loginPatient() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter Username: ");
            String username = input.next();
            System.out.print("Enter Password: ");
            String password = input.next();
            for (Patient p : patients) {
                if (p.getUserName().equals(username) && p.getPassword().equals(password)) {
                    System.out.println("Login successful! Welcome, " + p.getName());
                    p.Showmenu();
                    return;
                }
            }
            attempts--;
            if (attempts > 0) {
                System.out.println("Wrong username or password! (" + attempts + ") attempts remaining");
            }
        }
        System.out.println("Too many failed attempts, Returning to Main Menu...");
    }
 
    public void MainMenu() {
        int choice=0;
        do {
            System.out.println(">>>>>>>>>>>>>Main Menu<<<<<<<<<<<<<<<");
            System.out.println("1- Login as Admin");
            System.out.println("2- Login as Doctor");
            System.out.println("3- Login as Patient");
            System.out.println("4- Exit");
            System.out.print("So now,Enter your choice(from 1 to 4): ");
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
            choice = -1;
            }
            switch (choice) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    loginDoctor();
                    break;
                case 3:
                    loginPatient();
                    break;
                case 4:
                    System.out.println("You are leaving our Hospital system, Goodbye....");
                    break;
                default:
                    System.out.println("Invalid choice, Please Enter a number from(1 to 4)..");
                    break;
            }
        } while (choice != 4);
    }
 
    private void linkDoctorWithPatient() {
        for (Patient p : patients) {
            String assignedDoctorId = p.getRawAssignedDoctorId();
 
            if (assignedDoctorId == null || assignedDoctorId.isEmpty()) continue;
 
            for (Doctor d : doctors) {
                if (d.getDoctorid().equals(assignedDoctorId)) {
                    p.setAssignedDoctorId(d);
                    if (!d.getAssignedPatients().contains(p)) {
                        d.getAssignedPatients().add(p);
                    }
                    break;
                }
            }
        }
 
        for (Appointment app : appointments) {
            for (Patient p : patients) {
                if (p.getID().equals(app.getPatientId())) {
                    if (!p.getAppointments().contains(app)) {
                        p.getAppointments().add(app);
                    }
                }
            }
            for (Doctor d : doctors) {
                if (d.getID().equals(app.getDoctorId())) {
                    if (!d.getAppointments().contains(app)) {
                        d.getAppointments().add(app);
                    }
                }
            }
        }
    }
}
