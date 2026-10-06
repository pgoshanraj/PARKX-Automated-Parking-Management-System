package menu;

import model.Bike;
import model.Car;
import model.Vehicle;
import repository.FileStorageService;
import repository.HistoryRepository;
import repository.ParkingRepository;
import service.ParkingService;
import service.PaymentService;
import service.RevenueService;
import service.SearchService;
import model.ParkingSlot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    private ParkingRepository parkingRepository;
    private HistoryRepository historyRepository;
    private FileStorageService fileStorageService;

    private ParkingService parkingService;
    private SearchService searchService;
    private PaymentService paymentService;
    private RevenueService revenueService;

    private DateTimeFormatter formatter;

    public Menu() {

        scanner = new Scanner(System.in);

        parkingRepository = new ParkingRepository();
        historyRepository = new HistoryRepository();
        fileStorageService = new FileStorageService();

        parkingService = new ParkingService(parkingRepository);
        searchService = new SearchService(parkingRepository);
        paymentService = new PaymentService();
        revenueService = new RevenueService();

        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Load previously saved parking history
        ArrayList<String> savedHistory = fileStorageService.loadHistory();

        for (String record : savedHistory) {
            historyRepository.addHistory(record);
        }
        // Load previously active vehicles
        ArrayList<String> activeVehicles =
                fileStorageService.loadActiveVehicles();

        for (String record : activeVehicles) {

            String[] parts = record.split("\\|");

            if (parts.length == 5) {

                String vehicleNumber = parts[0];
                String vehicleType = parts[1];
                String ownerName = parts[2];
                String slotId = parts[3];

                LocalDateTime entryTime =
                        LocalDateTime.parse(parts[4]);

                Vehicle vehicle;

                if (vehicleType.equalsIgnoreCase("Car")) {

                    vehicle = new Car(
                            vehicleNumber,
                            ownerName,
                            entryTime);

                } else if (vehicleType.equalsIgnoreCase("Bike")) {

                    vehicle = new Bike(
                            vehicleNumber,
                            ownerName,
                            entryTime);

                } else {
                    continue;
                }

                for (ParkingSlot slot : parkingRepository.getParkingSlots()) {

                    if (slot.getSlotId().equals(slotId)
                            && !slot.isOccupied()) {

                        slot.parkVehicle(vehicle);
                        parkingRepository.addVehicle(vehicle);

                        break;
                    }
                }
            }
        }
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("       SMART PARKING MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Vehicle Entry");
            System.out.println("2. View Parking Slots");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Vehicle Exit");
            System.out.println("5. Parking History");
            System.out.println("6. Revenue Report");
            System.out.println("7. Exit");
            System.out.println("======================================");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        vehicleEntry();
                        break;

                    case 2:
                        parkingService.displayParkingSlots();
                        break;

                    case 3:
                        searchVehicle();
                        break;

                    case 4:
                        vehicleExit();
                        break;

                    case 5:
                        historyRepository.displayHistory();
                        break;

                    case 6:
                        revenueService.displayRevenue(
                                historyRepository.getParkingHistory());
                        break;

                    case 7:
                        fileStorageService.saveHistory(
                                historyRepository.getParkingHistory());
                        System.out.println("Thank you for using Smart Parking System.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter 1-7.");
                }

            } catch (NumberFormatException e) {

                System.out.println("Invalid input. Please enter a number.");

                choice = 0;
            }

        } while (choice != 7);
    }

    private void vehicleEntry() {

        System.out.println("\n========== VEHICLE ENTRY ==========");

        System.out.print("Enter vehicle number: ");
        String vehicleNumber = scanner.nextLine().trim().toUpperCase();

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be empty.");
            return;
        }

        System.out.print("Enter owner name: ");
        String ownerName = scanner.nextLine().trim();

        if (ownerName.isEmpty()) {
            System.out.println("Owner name cannot be empty.");
            return;
        }

        System.out.println("\nSelect Vehicle Type:");
        System.out.println("1. Car");
        System.out.println("2. Bike");
        System.out.print("Enter choice: ");

        String typeChoice = scanner.nextLine();

        Vehicle vehicle;

        if (typeChoice.equals("1")) {

            vehicle = new Car(vehicleNumber, ownerName);

        } else if (typeChoice.equals("2")) {

            vehicle = new Bike(vehicleNumber, ownerName);

        } else {

            System.out.println("Invalid vehicle type.");
            return;
        }

        boolean parked = parkingService.parkVehicle(vehicle);

        if (parked) {

            fileStorageService.saveActiveVehicles(
                    parkingRepository.getAllParkedVehicles());

            System.out.println("Entry Time      : "
                    + vehicle.getEntryTime().format(formatter));
        }
    }

    private void searchVehicle() {

        System.out.println("\n========== VEHICLE SEARCH ==========");

        System.out.print("Enter vehicle number: ");

        String vehicleNumber = scanner.nextLine();

        searchService.displayVehicle(vehicleNumber);
    }

    private void vehicleExit() {

        System.out.println("\n========== VEHICLE EXIT ==========");

        System.out.print("Enter vehicle number: ");

        String vehicleNumber = scanner.nextLine();

        Vehicle vehicle = parkingService.exitVehicle(vehicleNumber);

        if (vehicle == null) {
            return;
        }
        fileStorageService.saveActiveVehicles(
                parkingRepository.getAllParkedVehicles());

        LocalDateTime exitTime = LocalDateTime.now();

        long durationMinutes =
                paymentService.calculateDurationInMinutes(
                        vehicle.getEntryTime(),
                        exitTime);

        double fee =
                paymentService.calculateFee(
                        vehicle.getVehicleType(),
                        durationMinutes);

        paymentService.displayBill(
                vehicle.getVehicleType(),
                durationMinutes,
                fee);

        boolean paid = paymentService.processPayment(fee);

        if (paid) {

            String historyRecord =
                    "Vehicle: " + vehicle.getVehicleNumber()
                            + " | Type: " + vehicle.getVehicleType()
                            + " | Owner: " + vehicle.getOwnerName()
                            + " | Slot: " + vehicle.getAssignedSlot()
                            + " | Entry: " + vehicle.getEntryTime().format(formatter)
                            + " | Exit: " + exitTime.format(formatter)
                            + " | Fee: " + String.format("%.2f", fee);

            historyRepository.addHistory(historyRecord);

            fileStorageService.saveHistory(
                    historyRepository.getParkingHistory());

            System.out.println("Parking record saved successfully.");
        }
    }
}
