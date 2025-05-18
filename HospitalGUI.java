/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HospitalGUI extends JFrame {
    private final DatabaseHandler db = new DatabaseHandler();
    private final Map<String, ArrayList<String>> treatments = new HashMap<>();
    private final Map<String, ArrayList<String>> medicines = new HashMap<>();
    private final Map<String, ArrayList<String>> finances = new HashMap<>();

    private final JTextField idField = new JTextField(10);
    private final JTextField nameField = new JTextField(10);
    private final JTextField ageField = new JTextField(5);
    private final JTextField diagnosisField = new JTextField(15);
    private final JTextField searchField = new JTextField(10);
    private final JTextArea outputArea = new JTextArea(12, 50);

    public HospitalGUI() {
        setTitle("Hospital Management System");
        setSize(750, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Patient"));
        inputPanel.add(new JLabel("Patient ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Diagnosis:"));
        inputPanel.add(diagnosisField);

        JPanel actionPanel = new JPanel();
        JButton addButton = new JButton("Add Patient");
        JButton viewButton = new JButton("View All");
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton addTreatmentBtn = new JButton("Add Treatment");
        JButton viewTreatmentBtn = new JButton("View Treatment");
        JButton addMedicineBtn = new JButton("Add Medicine");
        JButton viewMedicineBtn = new JButton("View Medicine");
        JButton addFinancialBtn = new JButton("Add Financial");
        JButton viewFinancialBtn = new JButton("View Financial");

        actionPanel.add(addButton);
        actionPanel.add(viewButton);
        actionPanel.add(new JLabel("Patient ID:"));
        actionPanel.add(searchField);
        actionPanel.add(searchButton);
        actionPanel.add(deleteButton);
        actionPanel.add(addTreatmentBtn);
        actionPanel.add(viewTreatmentBtn);
        actionPanel.add(addMedicineBtn);
        actionPanel.add(viewMedicineBtn);
        actionPanel.add(addFinancialBtn);
        actionPanel.add(viewFinancialBtn);

        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Patient Records"));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(inputPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addPatient());
        viewButton.addActionListener(e -> viewPatients());
        searchButton.addActionListener(e -> searchPatient());
        deleteButton.addActionListener(e -> deletePatient());
        addTreatmentBtn.addActionListener(e -> addTreatmentAction());
        viewTreatmentBtn.addActionListener(e -> viewTreatmentAction());
        addMedicineBtn.addActionListener(e -> addMedicineAction());
        viewMedicineBtn.addActionListener(e -> viewMedicineAction());
        addFinancialBtn.addActionListener(e -> addFinancialAction());
        viewFinancialBtn.addActionListener(e -> viewFinancialAction());

        setVisible(true);
    }

    private void addPatient() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String diagnosis = diagnosisField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            idField.requestFocusInWindow();
            idField.selectAll();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) {
                JOptionPane.showMessageDialog(this, "Age must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age.");
            ageField.requestFocusInWindow();
            ageField.selectAll();
            return;
        }

        if (db.findPatientById(id) != null) {
            JOptionPane.showMessageDialog(this, "Patient ID already exists.");
            return;
        }

        Patient p = new Patient(id, name, age, diagnosis);
        if (db.insertPatient(p)) {
            clearFields();
            JOptionPane.showMessageDialog(this, "Patient added successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add patient.");
        }
    }

    private void viewPatients() {
        outputArea.setText("");
        ArrayList<Patient> list = db.getAllPatients();
        if (list.isEmpty()) {
            outputArea.setText("No patients found.");
        } else {
            for (Patient p : list) {
                outputArea.append(p.toString() + "\n");
            }
        }
    }

    private void searchPatient() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID to search.");
            return;
        }
        Object p = db.findPatientById(id);
        outputArea.setText((p != null) ? "Patient Found:\n" + p : "Patient not found.");
    }

    private void deletePatient() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID to delete.");
            return;
        }
        boolean success = db.deletePatient(id);
        outputArea.setText(success ? "Patient with ID " + id + " has been deleted." : "Patient not found.");
    }

    private void addTreatmentAction() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID for treatment.");
            return;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            searchField.requestFocusInWindow();
            searchField.selectAll();
            return;
        }
        String desc = JOptionPane.showInputDialog(this, "Enter treatment description:");
        if (desc != null && !desc.trim().isEmpty()) {
            db.addTreatment(id, desc);
            outputArea.setText("Treatment added for patient " + id);
        }
    }

    private void viewTreatmentAction() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID to view treatments.");
            return;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            searchField.requestFocusInWindow();
            searchField.selectAll();
            return;
        }
        outputArea.setText(db.getTreatment(id));
    }

    private void addMedicineAction() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID for medicine.");
            return;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            searchField.requestFocusInWindow();
            searchField.selectAll();
            return;
        }
        String med = JOptionPane.showInputDialog(this, "Enter medicine name:");
        if (med != null && !med.trim().isEmpty()) {
            db.addMedicine(id, med);
            outputArea.setText("Medicine added for patient " + id);
        }
    }

    private void viewMedicineAction() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID to view medicines.");
            return;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            searchField.requestFocusInWindow();
            searchField.selectAll();
            return;
        }
        outputArea.setText(db.getMedicines(id));
    }

    private void addFinancialAction() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID for financial record.");
            return;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            searchField.requestFocusInWindow();
            searchField.selectAll();
            return;
        }
        String chargeStr = JOptionPane.showInputDialog(this, "Enter total charge:");
        String depositedStr = JOptionPane.showInputDialog(this, "Enter total deposited:");
        String returnStr = JOptionPane.showInputDialog(this, "Enter total to return:");

        try {
            double charge = Double.parseDouble(chargeStr);
            double deposited = Double.parseDouble(depositedStr);
            double toReturn = Double.parseDouble(returnStr);
            db.addFinancialRecord(id, charge, deposited, toReturn);
            outputArea.setText("Financial record added for patient " + id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid financial input. Please enter numbers.");
        }
    }

    private void viewFinancialAction() {
        String id = searchField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter patient ID to view financial records.");
            return;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a number.");
            searchField.requestFocusInWindow();
            searchField.selectAll();
            return;
        }
        outputArea.setText(db.getFinancialRecord(id));
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        diagnosisField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HospitalGUI::new);
    }
}



