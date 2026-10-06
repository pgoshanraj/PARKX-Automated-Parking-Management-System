package service;
import model.Vehicle;
import repository.ParkingRepository;

public class SearchService {
    private ParkingRepository repository;

    public SearchService(ParkingRepository repository) {
        this.repository = repository;
    }

    public Vehicle searchVehicle(String vehicleNumber) {

        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return null;
        }

        return repository.findVehicle(vehicleNumber.trim().toUpperCase());
    }

    public void displayVehicle(String vehicleNumber) {

        Vehicle vehicle = searchVehicle(vehicleNumber);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.println("\n----- VEHICLE DETAILS -----");
        vehicle.displayInfo();
    }
}
