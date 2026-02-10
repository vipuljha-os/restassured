package CruiseBooking.Report;

import CruiseBooking.Model.BookingResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingResultTracker {

    private static final BookingResultTracker INSTANCE = new BookingResultTracker();
    private final List<BookingResult> results = Collections.synchronizedList(new ArrayList<>());

    private BookingResultTracker() {
    }

    public static BookingResultTracker getInstance() {
        return INSTANCE;
    }

    public void addResult(BookingResult result) {
        results.add(result);
    }

    public List<BookingResult> getResults() {
        return Collections.unmodifiableList(results);
    }

    public void writeReport() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "booking_results_" + timestamp + ".csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(BookingResult.csvHeader());
            for (BookingResult result : results) {
                writer.println(result.toCsvRow());
            }
            System.out.println("=== Report written to: " + fileName + " ===");
        } catch (IOException e) {
            System.err.println("Failed to write report: " + e.getMessage());
            e.printStackTrace();
        }

        printSummary();
    }

    private void printSummary() {
        int total = results.size();
        long step5Pass = results.stream().filter(r -> "PASS".equals(r.getStep5Status())).count();
        long step6Pass = results.stream().filter(r -> "PASS".equals(r.getStep6Status())).count();
        long step7Pass = results.stream().filter(r -> "PASS".equals(r.getStep7Status())).count();
        long fullPass = results.stream().filter(r ->
                "PASS".equals(r.getStep5Status())
                        && "PASS".equals(r.getStep6Status())
                        && "PASS".equals(r.getStep7Status())
        ).count();

        System.out.println("========================================");
        System.out.println("         BOOKING TEST SUMMARY           ");
        System.out.println("========================================");
        System.out.println("Total Scenarios:  " + total);
        System.out.println("Step 5 (Room):    " + step5Pass + "/" + total + " PASS");
        System.out.println("Step 6 (Preview): " + step6Pass + "/" + total + " PASS");
        System.out.println("Step 7 (Confirm): " + step7Pass + "/" + total + " PASS");
        System.out.println("Full Pass:        " + fullPass + "/" + total);
        System.out.println("Full Fail:        " + (total - fullPass) + "/" + total);
        System.out.println("========================================");
    }

    public void clear() {
        results.clear();
    }
}
