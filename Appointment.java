import java.util.ArrayList;
import java.util.Scanner;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;

    private static ArrayList<Appointment> appointments = new ArrayList<>();
    private static ArrayList<Patient> patients = new ArrayList<>();
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    // ===== Constructor =====
    public Appointment(String appointmentId, String patientId,
                                  String doctorId, String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId     = patientId;
        this.doctorId      = doctorId;
        this.date          = date;
        this.time          = time;
        this.status        = "confirmed";
    }

    // ===== Getters =====
    public String getAppointmentId() { return appointmentId; }
    public String getPatientId()     { return patientId; }
    public String getDoctorId()      { return doctorId; }
    public String getDate()          { return date; }
    public String getTime()          { return time; }
    public String getStatus()        { return status; }

    // ===== setStatus with Validation =====
    public void setStatus(String newStatus) {
        //ال lower case عشان ميقفش على الحرف كابيتل ولا سمول يقبله في اي حاله وال tirm عشان ي ignore المسافات برضو
    String updatedStatus = newStatus.toLowerCase().trim();
 
    if (this.status.equals("cancelled") && updatedStatus.equals("completed")) {
        System.out.println("Error: A cancelled appointment cannot be marked as completed!");
        return;
    }

    if (updatedStatus.equals("confirmed") || 
        updatedStatus.equals("completed") || 
        updatedStatus.equals("cancelled")) {
        
        this.status = updatedStatus;
    } else {
        System.out.println("Error: Invalid status!");
    }
}
    // ===== getAppointments() =====
    public static ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    // ===== createAppointment() =====
    public static boolean createAppointment(Scanner sc,ArrayList<Patient> patients, ArrayList<Doctor> doctors) {

        System.out.println("\n--- Create New Appointment ---");

        System.out.print("Appointment ID (e.g. A001): ");
        String appointmentId = sc.nextLine();
        
        for (Appointment a : appointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                System.out.println("Error: Appointment ID already exists!");
                   return false;
                }
            }
        System.out.print("Patient ID (e.g. P001): ");
        String patientId = sc.nextLine();
        Patient foundPatient = null;
        for (Patient p : patients) {
            if (p.getPatientid().equals(patientId)) {
                 foundPatient = p;
                 break;}
                }
                if (foundPatient == null) {
                    System.out.println("Error: Patient ID not found!");
                     return false;
                     }

        System.out.print("Doctor ID (e.g. D001): ");
        String doctorId = sc.nextLine();
        Doctor foundDoctor = null;
         for (Doctor d : doctors) {
             if (d.getDoctorid().equals(doctorId)) {
                foundDoctor = d;
                break;
             }
            }
            if (foundDoctor == null) {
                System.out.println("Error: Doctor ID not found!");
                return false;
            }
            if (!foundPatient.getAssignedDoctorId().equals(doctorId)) {
                System.out.println("Error: Patient is not assigned to this doctor!");
                return false;
            }

           /*  System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            while (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Error: Invalid date format! Please use YYYY-MM-DD (e.g., 2026-05-15).");
                System.out.print("Enter Date: ");
                date = sc.nextLine();
            }
                */
        System.out.print("Date (e.g. 15-5-2026): ");
        String date = sc.nextLine();
        if (date.trim().isEmpty()) {
            System.out.println("Error: Date cannot be empty!");
            return false;
        }

        System.out.print("Time (e.g. 10:30): ");
        String time = sc.nextLine();

        if (time.trim().isEmpty()) {
            System.out.println("Error: Time cannot be empty!");
            return false;
        }

        // Errorhandling 3: doctor not busy
        if (isDoctorBusy(doctorId, date, time)) {
            System.out.println("Error: Doctor already has an appointment at this time!");
            return false;
        }

        Appointment a = new Appointment(appointmentId, patientId, doctorId, date, time);
        appointments.add(a);
        System.out.println("Appointment created successfully!");
        return true;
    }

    // ===== Check if Doctor is Busy =====
    private static boolean isDoctorBusy(String doctorId, String date, String time) {
        for (Appointment a : appointments) {
            if (a.getDoctorId().equals(doctorId) &&
                a.getDate().equals(date)          &&
                a.getTime().equals(time)          &&
                !a.getStatus().equals("cancelled")) {
                return true;
            }
        }
        return false;
    }

    // ===== Cancel Appointment =====
    public static void cancelAppointment(String appointmentId) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                if (a.getStatus().equals("cancelled")) {
                    System.out.println("Appointment is already cancelled.");
                    return;
                }
                a.setStatus("cancelled");
                FileManager.saveAppointments(new ArrayList<>(appointments));
                System.out.println("Appointment cancelled successfully!");
                //appointments.remove(a);
                return;
            }
        }
        System.out.println("Appointment not found!");
    }

    // ===== Update Status =====
    public static void updateStatus(String appointmentId, String newStatus) {
    for (Appointment a : appointments) {
        if (a.getAppointmentId().equals(appointmentId)) {
            String oldStatus = a.getStatus();
            
            a.setStatus(newStatus);
            
            if (!oldStatus.equalsIgnoreCase(a.getStatus())) {
                System.out.println("Appointment " + appointmentId + " status updated to: " + a.getStatus());
            }
            return;
        }
    }
    System.out.println("Error: Appointment ID not found!");
}
    // ===== Convert to File String =====
    public String toFileString() {
        return appointmentId + "," + patientId + "," +
               doctorId + "," + date + "," + time + "," + status;
    }

    // ===== Read from File String =====
    public static Appointment fromFileString(String line) {
        String[] parts = line.split(",");
        Appointment a = new Appointment(
            parts[0], parts[1], parts[2], parts[3], parts[4]);
        a.status = parts[5];
        return a;
    }

    // ===== Display =====
    public void display() {
        System.out.println("====================");
        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Patient ID     : " + patientId);
        System.out.println("Doctor ID      : " + doctorId);
        System.out.println("Date           : " + date);
        System.out.println("Time           : " + time);
        System.out.println("Status         : " + status);
        System.out.println("====================");
    }
}
