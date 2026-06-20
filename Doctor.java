/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Doctor extends User{
    private String Specialization;
    private String Department;
    private String PhoneNumber;
   
    
    private ArrayList<Patient> AssignedPatients;
    private ArrayList<Appointment> Appointments;

    public Doctor(String ID, String Name, String UserName, String Password, String UserType, String Specialization, String Department, String PhoneNumber) {
        super(ID, Name, UserName, Password, UserType);
        this.Specialization=Specialization;
        this.Department=Department;
        this.PhoneNumber=PhoneNumber;
        
        AssignedPatients=new ArrayList<>();
        Appointments=new ArrayList<>();
    }
    
    public String getDoctorid(){
        return ID;
    }

    public String getDoctorName(){
        return Name;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String Specialization) {
        this.Specialization = Specialization;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public ArrayList<Patient> getAssignedPatients() {
        return AssignedPatients;
    }

    public void setAssignedPatients(ArrayList<Patient> AssignedPatients) {
        this.AssignedPatients = AssignedPatients;
    }

    public ArrayList<Appointment> getAppointments() {
        return Appointments;
    }

    public void setAppointments(ArrayList<Appointment> Appointments) {
        this.Appointments = Appointments;
    }

    public void viewProfile() {

    System.out.println("Doctor ID: " + ID);
    System.out.println("Name: " + Name);
    System.out.println("Username: " + UserName);
    System.out.println("Specialization: " + Specialization);
    System.out.println("Department: " + Department);
    System.out.println("Phone: " + PhoneNumber);

}
    
    public void viewassignedPatients(){
        if(AssignedPatients.isEmpty()){
            System.out.println("NO ASSIGNED PATIENTS");
            return;
        }
        for(Patient p : AssignedPatients){
            System.out.println("------------------");
            System.out.println("Patient ID: "+ p.getPatientid());
            System.out.println("Patient Name: "+ p.getPatientName());
            System.out.println("Phone: " + p.getPhoneNumber());
            System.out.println("------------------");       

            
        }
    }
    
    public void viewappointments(){
        if(Appointments.isEmpty()){
            System.out.println("NO CURRENT APPOINTMENTS");
            return;
        }
        for(Appointment a : Appointments){
            a.display();        
        }
    }

    //
    public void UpdateAppointment() {
        Scanner input=new Scanner(System.in);
        System.out.print("Enter Appointment ID: ");
        String appId = input.nextLine().trim();

        Appointment targetApp = null;
        for (Appointment a : Appointment.getAppointments()) {
            if (a.getAppointmentId().equals(appId) && a.getDoctorId().equals(this.ID)) {
                targetApp = a;
                break;
            }
        }
        if (targetApp == null) {
            System.out.println("Error: Appointment not found or does not belong to you!");
            return;
        }

        if (targetApp != null) {
            System.out.println("(Choose one of them): 1.confirmed  2.completed  3.cancelled");
            int statusChoice = input.nextInt();
            String newStatus = "";
        
            if (statusChoice == 1) newStatus = "confirmed";
            else if (statusChoice == 2) newStatus = "completed";
            else if (statusChoice == 3) newStatus = "cancelled";
            else {
            System.out.println("Invalid choice!");
            return;
        }
        Appointment.updateStatus(appId, newStatus);
        FileManager.saveAppointments(new ArrayList<>(Appointment.getAppointments()));
        } else {
            System.out.println("Appointment not found!");
        }
    }
    //


    public String toFileString(){
        return ID + "," + Name + "," + UserName + "," + Password + "," + UserType + ","
              + Specialization + "," + Department + "," + PhoneNumber ;
    }

    
    public static Doctor fromFileString(String line) {
        try{
        String[] data = line.split(",");

        return new Doctor(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7]);
        }
        catch(Exception e){
            System.out.println("No doctor has this data");
            return null;
        }        
    }
    
    @Override
    public void Showmenu(){
        Scanner in=new Scanner(System.in);
        int choice=0;
        while(choice!=5){
            
            System.out.println("DOCTOR MENU");
            System.out.println("(1) view my profile    "+" (2) view assigned patients");
            System.out.println("(3) View appointments  "+"(4) Update Appointment  "+"(5) Logout");
            choice =in.nextInt();
            
            switch(choice){
                case 1:
                    viewProfile();
                    break;
                    
                case 2:
                    viewassignedPatients();
                    break;
                    
                case 3:
                    viewappointments();
                    break;

                case 4:
                //
                    UpdateAppointment();
                    break;    
                //
                case 5:

                    System.out.println("you're Logging Out");
                    break;

                default:

                    System.out.println("Invalid Choice, Try again");    
            }
        }
        
        
    }
}