package repository;
import java.util.ArrayList;
public class HistoryRepository {
    private ArrayList<String> parkingHistory;

    public HistoryRepository() {
        parkingHistory = new ArrayList<>();
    }

    public void addHistory(String record) {
        parkingHistory.add(record);
    }

    public ArrayList<String> getParkingHistory() {
        return parkingHistory;
    }

    public void displayHistory() {

        if (parkingHistory.isEmpty()) {
            System.out.println("No parking history available.");
            return;
        }

        System.out.println("\n========== PARKING HISTORY ==========");

        for (String record : parkingHistory) {
            System.out.println(record);
            System.out.println("-------------------------------------");
        }
    }
}

