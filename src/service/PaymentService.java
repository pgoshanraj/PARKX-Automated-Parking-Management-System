package service;

import java.time.Duration;
import java.time.LocalDateTime;

public class PaymentService {

    private static final double CAR_RATE_PER_HOUR = 30.0;
    private static final double BIKE_RATE_PER_HOUR = 15.0;

    public long calculateDurationInMinutes(LocalDateTime entryTime,
                                           LocalDateTime exitTime) {

        return Duration.between(entryTime, exitTime).toMinutes();
    }

    public double calculateFee(String vehicleType, long durationMinutes) {

        double hourlyRate;

        if (vehicleType.equalsIgnoreCase("Car")) {
            hourlyRate = CAR_RATE_PER_HOUR;
        } else if (vehicleType.equalsIgnoreCase("Bike")) {
            hourlyRate = BIKE_RATE_PER_HOUR;
        } else {
            return 0;
        }

        // Minimum charge: 1 hour
        long hours = (long) Math.ceil(durationMinutes / 60.0);

        if (hours < 1) {
            hours = 1;
        }

        return hours * hourlyRate;
    }

    public void displayBill(String vehicleType,
                            long durationMinutes,
                            double fee) {

        long chargedHours = (long) Math.ceil(durationMinutes / 60.0);

        if (chargedHours < 1) {
            chargedHours = 1;
        }

        System.out.println("\n========== PARKING BILL ==========");
        System.out.println("Vehicle Type   : " + vehicleType);
        System.out.println("Duration       : " + durationMinutes + " minutes");
        System.out.println("Charged Hours  : " + chargedHours);
        System.out.printf("Parking Fee    : Rs. %.2f%n", fee);
        System.out.println("==================================");
    }

    public boolean processPayment(double amount) {

        if (amount <= 0) {
            return false;
        }

        System.out.println("Payment successful.");
        System.out.printf("Amount Paid    : Rs. %.2f%n", amount);

        return true;
    }
}
