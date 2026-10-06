package model;
import java.time.LocalDateTime;
public  abstract class Vehicle {
    private String vehicleNumber;
    private String ownerName;
    private String vehicleType;
    private String assignedSlot;
    private LocalDateTime entryTime;

    public Vehicle(String vehicleNumber, String ownerName, String vehicleType) {

        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.assignedSlot = "";
        this.entryTime = LocalDateTime.now();
    }
    public Vehicle(String vehicleNumber,
                   String ownerName,
                   String vehicleType,
                   LocalDateTime entryTime) {

        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.assignedSlot = "";
        this.entryTime = entryTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getAssignedSlot() {
        return assignedSlot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setAssignedSlot(String assignedSlot) {
        this.assignedSlot = assignedSlot;
    }

    public abstract void displayInfo();
}


