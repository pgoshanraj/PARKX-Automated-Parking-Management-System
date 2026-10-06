package repository;

import model.ParkingSlot;
import model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkingRepository {

    private ArrayList<ParkingSlot> parkingSlots;
    private HashMap<String, Vehicle> parkedVehicles;

    public ParkingRepository() {

        parkingSlots = new ArrayList<>();
        parkedVehicles = new HashMap<>();

        // Create car slots
        parkingSlots.add(new ParkingSlot("C1", "Car"));
        parkingSlots.add(new ParkingSlot("C2", "Car"));
        parkingSlots.add(new ParkingSlot("C3", "Car"));

        // Create bike slots
        parkingSlots.add(new ParkingSlot("B1", "Bike"));
        parkingSlots.add(new ParkingSlot("B2", "Bike"));
        parkingSlots.add(new ParkingSlot("B3", "Bike"));
    }

    public ArrayList<ParkingSlot> getParkingSlots() {
        return parkingSlots;
    }

    public HashMap<String, Vehicle> getParkedVehicles() {
        return parkedVehicles;
    }

    public void addVehicle(Vehicle vehicle) {

        String vehicleNumber =
                vehicle.getVehicleNumber().trim().toUpperCase();

        parkedVehicles.put(vehicleNumber, vehicle);
    }

    public Vehicle findVehicle(String vehicleNumber) {

        if (vehicleNumber == null) {
            return null;
        }

        String key = vehicleNumber.trim().toUpperCase();

        return parkedVehicles.get(key);
    }

    public void removeVehicle(String vehicleNumber) {

        if (vehicleNumber == null) {
            return;
        }

        String key = vehicleNumber.trim().toUpperCase();

        parkedVehicles.remove(key);
    }
    public ArrayList<Vehicle> getAllParkedVehicles() {
        return new ArrayList<>(parkedVehicles.values());
    }
}