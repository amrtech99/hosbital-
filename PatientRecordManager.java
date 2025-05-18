/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package javaapplication4;

import java.util.ArrayList;

public class PatientRecordManager {
    private ArrayList<PatientRecord> patientRecords = new ArrayList<>();

    public void addNewPatientRecord(String serviceType) {
       
        System.out.println("Adding patient for service type: " + serviceType);
    }

    public void searchOrEditPatientRecord(int recordNumber) {
        
        System.out.println("Searching or editing patient with record number: " + recordNumber);
    }

    public void searchOrEditPatientRecord(String fullName) {
        
        System.out.println("Searching or editing patient with name: " + fullName);
    }

    public void listPatientRecords(String option) {
        
        System.out.println("Listing patients by: " + option);
    }

    public void deletePatientRecord(int recordNumber) {
        
        System.out.println("Deleting patient record: " + recordNumber);
    }

    static class PatientRecord {
        String name;
        String address;
        int age;
        char sex;
        String diseaseDescription;
        int specialistRoomNumber;

        public PatientRecord(String name, String address, int age, char sex, String diseaseDescription, int specialistRoomNumber) {
            this.name = name;
            this.address = address;
            this.age = age;
            this.sex = sex;
            this.diseaseDescription = diseaseDescription;
            this.specialistRoomNumber = specialistRoomNumber;
        }
    }
}
