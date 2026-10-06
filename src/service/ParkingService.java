package service;

import model.ParkingSlot;
import model.Vehicle;
import repository.ParkingRepository;

public class ParkingService {

    private ParkingRepository repository;

    public ParkingService(ParkingRepository repository) {
        this.repository = repository;
    }

    public boolean parkVehicle(Vehicle vehicle) {

        // Check whether vehicle is already parked
        if (repository.findVehicle(vehicle.getVehicleNumber()) != null) {
            System.out.println("Vehicle is already parked.");
            return false;
        }

        // Find the first available suitable slot
        for (ParkingSlot slot : repository.getParkingSlots()) {

            if (!slot.isOccupied()
                    && slot.getVehicleType().equals(vehicle.getVehicleType())) {

                // Assign vehicle to the slot
                slot.parkVehicle(vehicle);

                // Store vehicle in repository
                repository.addVehicle(vehicle);

                System.out.println("Vehicle parked successfully.");
                System.out.println("Vehicle Number : "
                        + vehicle.getVehicleNumber());
                System.out.println("Assigned Slot  : "
                        + slot.getSlotId());

                return true;
            }
        }

        System.out.println("No suitable parking slot available.");
        return false;
    }

    public Vehicle exitVehicle(String vehicleNumber) {

        Vehicle vehicle = repository.findVehicle(vehicleNumber);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return null;
        }

        // Find the slot containing this vehicle
        for (ParkingSlot slot : repository.getParkingSlots()) {

            if (slot.isOccupied()
                    && slot.getParkedVehicle() == vehicle) {

                // Save the slot ID before releasing the vehicle
                String slotId = slot.getSlotId();
                vehicle.setAssignedSlot(slotId);

// Release the parking slot
                slot.releaseVehicle();

// Restore slot ID for history record
                vehicle.setAssignedSlot(slotId);

// Remove vehicle from active parking records
                repository.removeVehicle(vehicleNumber);

                System.out.println("Vehicle exited successfully.");
                System.out.println("Vehicle Number : "
                        + vehicle.getVehicleNumber());

                return vehicle;
            }
        }

        System.out.println("Parking slot information not found.");
        return null;
    }

    public void displayParkingSlots() {

        System.out.println("\n----- PARKING SLOT STATUS -----");

        for (ParkingSlot slot : repository.getParkingSlots()) {
            slot.displaySlot();
        }
    }
}
