package model;

import java.time.LocalDateTime;

public class Bike extends Vehicle {

    public Bike(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName, "Bike");
    }

    public Bike(String vehicleNumber,
                String ownerName,
                LocalDateTime entryTime) {
        super(vehicleNumber, ownerName, "Bike", entryTime);
    }

    @Override
    public void displayInfo() {
        System.out.println("Bike");
        System.out.println("Vehicle Number : " + getVehicleNumber());
        System.out.println("Owner Name     : " + getOwnerName());
        System.out.println("Assigned Slot  : " + getAssignedSlot());
    }
}
