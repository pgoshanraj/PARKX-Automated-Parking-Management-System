package model;

public class ParkingSlot {

    private String slotId;
    private String vehicleType;
    private boolean occupied;
    private Vehicle parkedVehicle;

    public ParkingSlot(String slotId, String vehicleType) {

        this.slotId = slotId;
        this.vehicleType = vehicleType;
        this.occupied = false;
        this.parkedVehicle = null;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void parkVehicle(Vehicle vehicle) {

        this.parkedVehicle = vehicle;
        this.occupied = true;

        vehicle.setAssignedSlot(slotId);
    }

    public void releaseVehicle() {

        if (parkedVehicle != null) {
            parkedVehicle.setAssignedSlot("");
        }

        parkedVehicle = null;
        occupied = false;
    }

    public void displaySlot() {

        System.out.print(slotId + " - ");

        if (occupied) {
            System.out.println("Occupied");
        } else {
            System.out.println("Available");
        }
    }
}