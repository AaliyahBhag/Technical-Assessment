//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] dates = {"2024-05-17", "2024-08-15", "2024-11-13", "2025-02-11", "2025-05-12", "2025-08-10", "2025-11-08", "2026-02-06", "2026-05-07"};
        double[] bidRates = {4.50, 5.00, 6.00, 7.20, 7.60, 8.10, 9.00, 10.00, 11.30}; //Rates are in % - hence 4.50%
        double[] askRates = {4.55, 5.05, 6.05, 7.25, 7.65, 8.15, 9.05, 10.05, 11.35};

        // Instantiate the yield curve
        YieldCurve yieldCurve = new YieldCurve(dates, bidRates, askRates);

        // Create a scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to input the date
        System.out.print("Enter the date in yyyy-MM-dd format: ");
        String dateString = scanner.nextLine();
        // Parse the user input date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);

        // Prompt the user to specify the rate type (bid, mid, ask)
        System.out.print("Enter the rate type (bid, mid or ask): ");
        String rateType = scanner.nextLine();


        // Get and print the rate for the specified date and rate type
        try {
            double rate = yieldCurve.getRate(date, rateType);
            String rateFormatted = String.format(Locale.US, "%.2f", rate);
            System.out.println("Rate on " + date + ": " + rateFormatted + "%");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        // Close the scanner
        scanner.close();
    }
}