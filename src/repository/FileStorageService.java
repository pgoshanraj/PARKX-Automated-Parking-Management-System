package repository;

import model.Vehicle;

import java.io.*;
import java.util.ArrayList;

public class FileStorageService {

    private static final String HISTORY_FILE = "parking_history.txt";
    private static final String ACTIVE_FILE = "active_parking.txt";

    // ================= HISTORY STORAGE =================

    public void saveHistory(ArrayList<String> history) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(HISTORY_FILE))) {

            for (String record : history) {
                writer.write(record);
                writer.newLine();
            }

            System.out.println("Parking history saved successfully.");

        } catch (IOException e) {
            System.out.println("Error while saving parking history.");
        }
    }

    public ArrayList<String> loadHistory() {

        ArrayList<String> history = new ArrayList<>();

        File file = new File(HISTORY_FILE);

        if (!file.exists()) {
            return history;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(HISTORY_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    history.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error while loading parking history.");
        }

        return history;
    }

    // ================= ACTIVE PARKING STORAGE =================

    public void saveActiveVehicles(ArrayList<Vehicle> vehicles) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(ACTIVE_FILE))) {

            for (Vehicle vehicle : vehicles) {

                writer.write(
                        vehicle.getVehicleNumber() + "|" +
                                vehicle.getVehicleType() + "|" +
                                vehicle.getOwnerName() + "|" +
                                vehicle.getAssignedSlot() + "|" +
                                vehicle.getEntryTime()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error while saving active parking.");
        }
    }

    public ArrayList<String> loadActiveVehicles() {

        ArrayList<String> vehicles = new ArrayList<>();

        File file = new File(ACTIVE_FILE);

        if (!file.exists()) {
            return vehicles;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(ACTIVE_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    vehicles.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error while loading active parking.");
        }

        return vehicles;

    }
}
