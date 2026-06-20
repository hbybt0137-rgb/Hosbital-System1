import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Patient extends User {
    //Scanner input = new Scanner(System.in);
    private double age;
    private String gender;
    private String phone;
    private Doctor assignedDoctor;
    private List<Appointment> appointments;
    //private static int appointmentCounter = 0;
    


    private String rawAssignedDoctorId = "";

    public String getRawAssignedDoctorId() { return rawAssignedDoctorId; }
    public void setRawAssignedDoctorId(String id) { this.rawAssignedDoctorId = id; }

   
public Patient(String ID, String Name, String UserName, String Password, String UserType, double age, String gender, String phone) {
       super(ID, Name, UserName, Password, "Patient");
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.appointments = new ArrayList<>();
    }
   //SETTERS AND GETTERS
    public String getPatientid(){
        return getID();
    }public String getPatientName(){
        return getName();
    }
public void setAssignedDoctorId(Doctor doctor) {
    this.assignedDoctor = doctor; 
}

public String getAssignedDoctorId() {
    return assignedDoctor != null ? assignedDoctor.getID() : "No Doctor Assigned"; // بس getter
}
    public double getAge() {return age;}
    public void setAge(double age) {this.age = age;}
    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}
    public String getPhoneNumber() { return phone; }
    public void setPhoneNumber(String phone) {this.phone = phone;}
    public Doctor getAssignedDoctor() {return assignedDoctor;}
    public void setAssignedDoctor(Doctor doctor) {this.assignedDoctor = doctor;}

    public List<Appointment> getAppointments() {return appointments;}
    public void setAppointments(List<Appointment> appointments) {this.appointments = appointments;}

    // METHODS OF FILEMANAGER
    public String toFileString() {
    String doctorId = assignedDoctor != null ? assignedDoctor.getID() : "";
    return getID() + "," + getName() + "," + getUserName() + ","
         + getPassword() + "," + age + "," + gender + "," + phone + "," + doctorId;
}  
    public static Patient fromFileString(String data) {
    String[] parts = data.split(",", -1);
    String id = parts[0];
    String name = parts[1];
    String username = parts[2];
    String password = parts[3];
    double age = Double.parseDouble(parts[4]);
    String gender = parts[5];
    String phone = parts[6];

    Patient p = new Patient(id, name, username, password, "Patient", age, gender, phone);
    if (parts.length >= 8 && !parts[7].isEmpty()) {
        p.setRawAssignedDoctorId(parts[7]);
    }
    return p;
}

    //Patient Menu
public void Showmenu() {
    Scanner input = new Scanner(System.in);
        int choice;
    do {
        System.out.println("=== Patient Menu ===");
        System.out.println("1: View My Profile");
        System.out.println("2: View Assigned Doctor");
        System.out.println("3: View My Appointments");
        System.out.println("4: Book Appointment");
        System.out.println("5: Cancel Appointment");
        System.out.println("6: Logout");
        System.out.println("Choose an option: ");
            
        choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1:
                    viewProfile();
                    break;
            case 2:
                    viewAssignedDoctor();
                    break;
            case 3:
                    viewMyAppointments();
                    break;
            case 4:
                    bookAppointment();
                    break;
            case 5:
                    cancelAppointment();
                    break;
            case 6:
                    System.out.println("Logging out");
                    break;
            default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 6);}
        //method 1
        public void viewProfile() {
        System.out.println("--- My Profile ---");
        System.out.println("ID      : " + getID());
        System.out.println("Name    : " + getName());
        System.out.println("Username: " + getUserName());
        System.out.println("Age     : " + age);
        System.out.println("Gender  : " + gender);
        System.out.println("Phone   : " + phone);}
        //method 2
        public void viewAssignedDoctor() {
        if (assignedDoctor != null) {
            System.out.println("--- Assigned Doctor ---");
            System.out.println("ID   : " + assignedDoctor.getID());
            System.out.println("Name : " + assignedDoctor.getName());
            System.out.println("Specialization: " + assignedDoctor.getSpecialization());
        } else {
            System.out.println("No doctor assigned");
        } //method 3
    } public void viewMyAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("--- My Appointments ---");
            for (Appointment a : appointments) { 
                a.display();
            }
        }
    } //method 4
    public void bookAppointment() {
        Scanner input=new Scanner(System.in);
    if (assignedDoctor == null) {
        System.out.println("Error: You cannot book an appointment without being assigned to a doctor!");
        return;
    }    
    System.out.println("Enter Doctor ID: ");
    String doctorId = input.nextLine();
    if (!doctorId.equals(assignedDoctor.getID())) {
        System.out.println("Error: You can only book with your assigned doctor ("
                       + assignedDoctor.getID() + ")!");
        return;
    }
    System.out.println("Enter Date  ");
    String date = input.nextLine();
    System.out.println("Enter Time ");
    String time = input.nextLine();
//
    String appointmentId = "A" + System.currentTimeMillis() % 10000;
//
    Appointment newAppointment = new Appointment(appointmentId, getID(), doctorId, date, time);
    this.appointments.add(newAppointment);
    Appointment.getAppointments().add(newAppointment); 
    if (assignedDoctor != null && !assignedDoctor.getAppointments().contains(newAppointment)) {
    assignedDoctor.getAppointments().add(newAppointment);
    FileManager.saveAppointments(new ArrayList<>(Appointment.getAppointments()));
    System.out.println("Appointment booked successfully");
    }
}


public void cancelAppointment() {
    Scanner input=new Scanner(System.in);
    System.out.println("Enter Appointment ID to cancel: ");
    String appointmentId = input.nextLine();

    boolean found = false;
    for (Appointment a : appointments) {
        if (a.getAppointmentId().equals(appointmentId)) {
            a.setStatus("cancelled");
            FileManager.saveAppointments(new ArrayList<>(Appointment.getAppointments()));
            System.out.println("Appointment cancelled successfully");
            found = true;
            break;
        }
    }
    if (!found) System.out.println("Appointment not found");
}
}