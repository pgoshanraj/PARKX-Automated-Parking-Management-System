package service;
import java.util.ArrayList;
public class RevenueService {
    public double calculateTotalRevenue(ArrayList<String> parkingHistory) {

        double totalRevenue = 0.0;

        for (String record : parkingHistory) {

            String[] parts = record.split("\\|");

            for (String part : parts) {

                part = part.trim();

                if (part.startsWith("Fee:")) {

                    String feeValue = part.substring(4).trim();

                    totalRevenue += Double.parseDouble(feeValue);
                }
            }
        }

        return totalRevenue;
    }

    public void displayRevenue(ArrayList<String> parkingHistory) {

        double totalRevenue = calculateTotalRevenue(parkingHistory);

        System.out.println("\n========== REVENUE REPORT ==========");
        System.out.printf("Total Revenue : Rs. %.2f%n", totalRevenue);
        System.out.println("====================================");
    }
}
