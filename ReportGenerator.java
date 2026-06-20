import java.util.ArrayList;

public class ReportGenerator {

    // ===== generateReports() — required method name =====
    public static void generateReports(ArrayList<Doctor> doctors,
                                        ArrayList<Patient> patients,
                                        ArrayList<Appointment> appointments) {

        System.out.println("\n=============================");
        System.out.println("       HOSPITAL REPORT       ");
        System.out.println("=============================");

        // 1. Total Doctors and Patients
        System.out.println("\n--- Total ---");
        System.out.println("Total Doctors  : " + doctors.size());
        System.out.println("Total Patients : " + patients.size());

        // 2. Appointments by Status
        int confirmed = 0, completed = 0, cancelled = 0;
        for (Appointment a : appointments) {
            if (a.getStatus().equals("confirmed"))  confirmed++;
            else if (a.getStatus().equals("completed")) completed++;
            else if (a.getStatus().equals("cancelled")) cancelled++;
        }

        System.out.println("\n--- Appointments by Status ---");
        System.out.println("Total      : " + appointments.size());
        System.out.println("Confirmed  : " + confirmed);
        System.out.println("Completed  : " + completed);
        System.out.println("Cancelled  : " + cancelled);

        // 3. Top 3 Doctors
        System.out.println("\n--- Top 3 Doctors ---");
        topDoctors(doctors, appointments);

        System.out.println("=============================\n");
    }

    // ===== Top 3 Doctors Helper =====
    private static void topDoctors(ArrayList<Doctor> doctors,
                                    ArrayList<Appointment> appointments) {

        if (doctors.isEmpty()) {
            System.out.println("No doctors found!");
            return;
        }
        ArrayList<Doctor> doctorsCopy = new ArrayList<>(doctors);
        // Count appointments per doctor
        int[] count = new int[doctorsCopy.size()];
        for (int i = 0; i < doctorsCopy.size(); i++) {
            for (Appointment a : appointments) {
                if (a.getDoctorId().equals(doctorsCopy.get(i).getID())) {
                    count[i]++;
                }
            }
        }

        // Bubble sort descending
        for (int i = 0; i < doctorsCopy.size() - 1; i++) {
            for (int j = 0; j < doctorsCopy.size() - i - 1; j++) {
                if (count[j] < count[j + 1]) {
                    int tempCount    = count[j];
                    count[j]         = count[j + 1];
                    count[j + 1]     = tempCount;

                    Doctor tempDoc   = doctorsCopy.get(j);
                    doctorsCopy.set(j,   doctorsCopy.get(j + 1));
                    doctorsCopy.set(j+1, tempDoc);
                }
            }
        }

        // Print top 3
        int top = Math.min(3, doctorsCopy.size());
        for (int i = 0; i < top; i++) {
            System.out.println((i + 1) + ". Dr. " + doctorsCopy.get(i).getName() +
                               " — Appointments: " + count[i]);
        }
    }
}