package model;

import java.time.LocalDateTime;

public class Car extends Vehicle {

    public Car(String vehicleNumber, String ownerName) {
        super(vehicleNumber, ownerName, "Car");
    }

    public Car(String vehicleNumber,
               String ownerName,
               LocalDateTime entryTime) {
        super(vehicleNumber, ownerName, "Car", entryTime);
    }

    @Override
    public void displayInfo() {
        System.out.println("Car");
        System.out.println("Vehicle Number : " + getVehicleNumber());
        System.out.println("Owner Name     : " + getOwnerName());
        System.out.println("Assigned Slot  : " + getAssignedSlot());
    }
}
