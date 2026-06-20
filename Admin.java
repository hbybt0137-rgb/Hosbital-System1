import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
    private ArrayList<Doctor> doctors = new ArrayList<>();//from Doctor class
    private ArrayList<Patient> patients = new ArrayList<>();//from Patient class
    private ArrayList<Appointment> appointments = Appointment.getAppointments();//from AppointmentManagement class

    public Admin(String ID ,String Name, String UserName,String Password,String UserType){
        super(ID,Name,UserName,Password,"Admin");
    }
    public Admin(){
        super();
    }

    public void AddDoctor(){
        Scanner input = new Scanner(System.in);
        String Name = "" ;
        boolean validN=false;
        while(!validN){
            System.out.println("Enter Doctor Name");
            Name=input.nextLine();
            if(Name.matches(".*\\d.*")){
                System.out.println("Error Name Contains Numbers");
            }else if(!Name.matches("[a-zA-Z\\s]+")){
                System.out.println("Error Name Contains Special Characters");
            }
            else if(Name.trim().isEmpty()){
                System.out.println("Error Name Has No Value");
            }else{
                validN=true;
            }
        }
        String ID = "";
        boolean validId=false;
        while (!validId) {
            System.out.println("Enter Doctor ID");
            ID=input.nextLine();
            boolean isexist=false;
            for(Doctor d : doctors){ 
                if(d.getDoctorid().equals(ID)){
                    isexist=true;
                    break;
                }
            }if(isexist){
                System.out.println("ID Is Already Exists");
            }else{
                validId=true;
            }
        }
        boolean validS=false;
        String spec = "";
        while(!validS){
            System.out.println("Enter Specialization");
            spec=input.nextLine();
            if(spec.matches(".*\\d.*")){
                System.out.println("Error Specialization Contains Numbers");
            }else if(!spec.matches("[a-zA-Z\\s]+")){
                System.out.println("Error Specialization Contains Special Characters");
            }else if(spec.trim().isEmpty()){
                System.out.println("Error Specialization Has No Value");
            }else{
            validS=true;
            }
        }
        String username = "";
        while (username.trim().isEmpty()) {
            System.out.println("Enter User Name");
            username = input.nextLine();
            if (username.trim().isEmpty()) System.out.println("Error: Username cannot be empty");
        }
        String password = "";
        while (password.trim().isEmpty()) {
            System.out.println("Enter Password");
            password = input.nextLine();
            if (password.trim().isEmpty()) System.out.println("Error: Password cannot be empty");
        }
        boolean validD=false;
        String department="";
        while(!validD){
            System.out.println("Enter Department");
            department=input.nextLine();
            if(department.matches(".*\\d.*")){
                System.out.println("Error Department Contains Numbers");
            }else if(!department.matches("[a-zA-Z\\s]+")){
                System.out.println("Error Department Contains Special Characters");
            }else if(department.trim().isEmpty()){
                System.out.println("Error Department Has No Value");
            }else{
            validD=true;
            }
        }
        boolean validP=false;
        String phonenumber= "";
        while(!validP){
            System.out.println("Enter Phone Number");
            phonenumber=input.nextLine();
            if(phonenumber.trim().isEmpty()){
                System.out.println("Error Phone Number Has No Value");
            }else if(!phonenumber.matches("\\d+")){
                System.out.println("Error Phone Number Contains Characters");
            }else if(phonenumber.length()!=11){
                System.out.println("Invalid Phone Number length");
            }else {
                validP=true;
            }
        }
        Doctor d = new Doctor(ID,Name,username,password,"Doctor",spec, department,phonenumber);//from Doctor class
        doctors.add(d);//add object to arraylist (member 5 change)
        FileManager.saveDoctors(doctors);
        if(validP){ 
            System.out.println("Doctor Added");

}
    }


    public void RemoveDoctor( ){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Doctor ID To Remove");
        String id = input.nextLine();
        Doctor doctor=SearchDoctorbyID(id);
        if(doctor!=null){
            doctors.remove(doctor);
            appointments.removeIf(a -> a.getDoctorId().equals(doctor.getDoctorid()));
            Appointment.getAppointments().removeIf(a -> a.getDoctorId().equals(doctor.getDoctorid()));
            for (Patient p : patients) {
            p.getAppointments().removeIf(a -> a.getDoctorId().equals(doctor.getDoctorid()));
            }
            FileManager.saveAppointments(new ArrayList<>(Appointment.getAppointments()));
            FileManager.saveDoctors(doctors);
            System.out.println("Doctors saved successfully in txt file");
            System.out.println("Doctor "+ doctor.getDoctorName() + " Deleted");
        } else{
            System.out.println("Doctor Not Found");
        }
    }


    public void RemoveAppointment(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Appointment ID To Remove");
        String id = input.nextLine();
        Appointment.cancelAppointment(id);

          Appointment.getAppointments().removeIf(a -> a.getAppointmentId().equals(id));
    appointments.removeIf(a -> a.getAppointmentId().equals(id));
        FileManager.saveAppointments(new ArrayList<>(Appointment.getAppointments()));
    }


    public void RegisterPatient(){
        Scanner input = new Scanner(System.in);
        String Name = "" ;
        boolean validN=false;
        while(!validN){
            System.out.println("Enter Patient Name");
            Name=input.nextLine();
            if(Name.matches(".*\\d.*")){
                System.out.println("Error Name Contains Numbers");
            }else if(!Name.matches("[a-zA-Z\\s]+")){
                System.out.println("Error Name Contains Special Characters");
            }
            else if(Name.trim().isEmpty()){
                System.out.println("Error Name Has No Value");
            }else{
                validN=true;
            }
        }
        String ID = "";
        boolean validId=false;
        while (!validId) {
            System.out.println("Enter Patient ID");
            ID=input.nextLine();
            boolean isexist=false;
            for(Patient p : patients){ 
                if(p.getPatientid().equals(ID)){
                    isexist=true;
                    break;
            }
        }
            if(isexist){
                System.out.println("ID Is Already Exists");
            }else{
                validId=true;
            }
        }
        boolean validG=false;
        String Gender = "";
        while(!validG){
            System.out.println("Enter Gender");
            Gender=input.nextLine();
            if(Gender.matches(".*\\d.*")){
                System.out.println("Error Gender Contains Numbers");
            }else if(!Gender.matches("[a-zA-Z\\s]+")){
                System.out.println("Error Gender Contains Special Characters");
            }
            else if(Gender.trim().isEmpty()){
                System.out.println("Error Gender Has No Value");
            }else{
                validG=true;
            }
        }
        System.out.println("Enter User Name");
        String username=input.nextLine();
        System.out.println("Enter Password");
        String password=input.nextLine();
        boolean validP=false;
        String phonenumber= "";
        while(!validP){
            System.out.println("Enter Phone Number");
            phonenumber=input.nextLine();

if(phonenumber.trim().isEmpty()){
                System.out.println("Error Phone Number Has No Value");
            }else if(!phonenumber.matches("\\d+")){
                System.out.println("Error Phone Number Contains Characters");
            }else if(phonenumber.length()!=11){
                System.out.println("Invalid Phone Number length");
            }else {
                validP=true;
            }
        }
        double Age= 0;
        boolean validA=false;
        while (!validA) {
            System.out.println("Enter Age");  
            if(input.hasNextBigDecimal()){
                Age=input.nextDouble();
                input.nextLine();
                if(Age > 0 && Age < 120){
                    validA=true;
                }else{
                    System.out.println("Error Age Must Be A Positive Number , between(0:120)");
                }
            }else{
                System.out.println("Error Age Has Characters");
                input.next();
            }
        }
        Patient p = new Patient(ID,Name,username,password,"Patient",Age,Gender,phonenumber);//from Patient class
        patients.add(p);//add object to arraylist (member 5 change)
        FileManager.savePatients(patients);
        if(validP){ 
            System.out.println("Patient Added");
        }
    }

    public void AssignPatienttoDoctor() {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter Patient ID");
    String patientID = input.nextLine();
    System.out.println("Enter Doctor ID");
    String doctorID = input.nextLine();

    Patient patient = SearchPatientbyID(patientID);
    Doctor doctor = SearchDoctorbyID(doctorID);

    if (patient != null && doctor != null) {
        if (patient.getAssignedDoctor() != null) {
            patient.getAssignedDoctor().getAssignedPatients().remove(patient);
        }

        patient.setAssignedDoctorId(doctor);

        if (!doctor.getAssignedPatients().contains(patient)) {
            doctor.getAssignedPatients().add(patient);
        }

        FileManager.savePatients(patients);
        FileManager.saveDoctors(doctors);
        System.out.println("Assignment saved to file successfully!");
    } else {
        System.out.println("Doctor Or Patient Not Found");
    }
}


   /*  public void AssignPatienttoDoctor (){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Patient ID");
        String patientID = input.nextLine();
        System.out.println("Enter Doctor ID");
        String doctorID = input.nextLine();

        Patient patient=SearchPatientbyID(patientID);//return object or null and store it at object from Patient class
        Doctor doctor=SearchDoctorbyID(doctorID); //return object or null and store it at object from Doctor class

        if(patient!=null&&doctor!=null){
            patient.setAssignedDoctorId(doctor);//method from Patient class
            doctor.getAssignedPatients().add(patient);//method from Doctor class
            //
            FileManager.savePatients(patients); 
            System.out.println("Assignment saved to file successfully!");
            //
        }else{
            System.out.println("Doctor Or Patient Not Found");
        }
    }
*/

    public void CreateAppointment(){
        Scanner input = new Scanner(System.in);
        int sizeBefore = Appointment.getAppointments().size();
        boolean success = Appointment.createAppointment(input, patients, doctors);
        int sizeAfter = Appointment.getAppointments().size();
        if (success && sizeAfter > sizeBefore) {
            Appointment last = Appointment.getAppointments().get(sizeAfter - 1);
            if (!appointments.contains(last)) {
                appointments.add(last);
            }
            FileManager.saveAppointments(new ArrayList<>(Appointment.getAppointments()));
            System.out.println("Appointment Sent To Appointment Management");
        }
    }


    public Patient SearchPatientbyID(String id){  // method from Patient type thats return object
        for(Patient p : patients){//member 5 change
            if(p.getPatientid().equals(id)){// getPatientid from Patient class
                return p;
            }
        }
        return null;
    }


    public Doctor SearchDoctorbyID(String id){ // method from Doctor type thats return object
        for(Doctor d : doctors){//member 5 change
            if(d.getDoctorid().equals(id)){//getDoctorid from Doctor class
                return d;
            }
        }
        return null;
    }

    public void GenerateReports(){
        ReportGenerator.generateReports(doctors,patients,appointments);
    }


    public void SaveData(){
        FileManager.savedata(doctors,appointments,patients);//savedata from FileManager class that is static ,member 5 change
    }

public void ViewAllDoctors(){
        System.out.println("All Doctors");
        for(Doctor d : doctors){ //from Doctor class,member 5 change
            System.out.println("Dr "+ d.getDoctorName());//getDoctorName from Doctor class
        }
    }


    public void ViewAllPatients(){
        System.out.println("All Patients");
        for(Patient p : patients){ //from Patient class,member 5 change
            System.out.println(p.getPatientName());//getPatientName from Patient class
        }
    }


    public void ViewAllAppointments(){
        for(Appointment A : appointments){//from Appointment class,member 5 change
            A.display();//getAppointments from Appointment class
        }
    }


    public void Showmenu(){
        Scanner input = new Scanner(System.in);
        int choice=0;
        while(choice!=14){ 
            System.out.println("Admin Menu:");
            System.out.println("1. Add Doctor\n2. Register Patient\n3. Assign Patient to Doctor\n4. Create Appointment \n5. View All Doctors \n6. View All Patients \n7. View All Appointments \n8. Search Patient by ID \n9. Search Doctor by ID \n10. Generate Reports \n11. Save Data \n12. Remove Doctor  \n13. Remove Appointment  \n14. Logout ");
            choice=input.nextInt();
            input.nextLine();


            switch (choice){
                case(1):AddDoctor(); break;
                case(2):RegisterPatient(); break;
                case(3):AssignPatienttoDoctor(); break;
                case 4:
                    CreateAppointment();
                    break;
                case(5):ViewAllDoctors(); break;
                case(6):ViewAllPatients();break;
                case(7):ViewAllAppointments();break;
                case(8):{ 
                    System.out.println("Enter Patient ID");
                    String P_id=input.nextLine();
                    Patient p = SearchPatientbyID(P_id);
                    if(p==null){System.out.println("Patient Not Found");}
                    else{
                        System.out.println("=== Patient Found ===");
                         System.out.println("ID      : " + p.getPatientid());
                         System.out.println("Name    : " + p.getPatientName());
                         System.out.println("Age     : " + p.getAge());
                         System.out.println("Gender  : " + p.getGender());
                         System.out.println("Phone   : " + p.getPhoneNumber());
                         System.out.println("Doctor  : " + p.getAssignedDoctorId());}
                         break;
                        }
                case(9):{ 
                    System.out.println("Enter Doctor ID");
                    String D_id=input.nextLine();
                    Doctor d = SearchDoctorbyID(D_id);
                    if(d==null){System.out.println("Doctor Not Found");}
                    else{
                        System.out.println("=== Doctor Found ===");
                        System.out.println("ID             : " + d.getDoctorid());
                        System.out.println("Name           : " + d.getDoctorName());
                        System.out.println("Specialization : " + d.getSpecialization());
                        System.out.println("Department     : " + d.getDepartment());
                        System.out.println("Phone          : " + d.getPhoneNumber());
                    }
                    break;}
                case(10):GenerateReports(); break;
                case(11):SaveData();break;
                case(12):RemoveDoctor(); break;
                case(13):RemoveAppointment();break;
                case(14):System.out.println("Loging Out...");break;
                default:System.out.println("invalid Choice");break;
            }

        }
    //input.close();  شيلته عشان لو قفلتي السكانر هنا هيتعطل في كلاس ال hospital
    }

    //ضيفت دول عشان هستخدمهم في كلاس ال hospital system
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }
    public ArrayList<Patient> getPatients() {
        return patients;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    //
}




