import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Welcome to the South African Government Bond Pricer!\n");
            System.out.print("Enter the Rand nominal amount of the SAGB you would like to value: \n");
            double nominalValue = Double.parseDouble(scanner.nextLine());


            System.out.println("Would you like to use example inputs? (1/2/no) ");
            String useExample = scanner.nextLine().trim().toLowerCase();

            LocalDate settlementDate, maturityDate, lastCouponDate;
            double couponRate, yieldToMaturity;

            switch (useExample) {
                case "1" -> {
                    settlementDate = LocalDate.parse("2017-02-07", formatter);
                    maturityDate = LocalDate.parse("2026-12-21", formatter);
                    lastCouponDate = LocalDate.parse("2016-12-21", formatter);
                    couponRate = 0.105;
                    yieldToMaturity = 0.0875;

                    System.out.println("\nUsing example 1 inputs for the R186");
                }
                case "2" -> {
                    settlementDate = LocalDate.parse("2024-05-16", formatter);
                    maturityDate = LocalDate.parse("2032-03-31", formatter);
                    lastCouponDate = LocalDate.parse("2024-03-31", formatter);
                    couponRate = 0.0825;
                    yieldToMaturity = 0.095;

                    System.out.println("\nUsing example 2 inputs for the R2032");
                }
                case "no" -> {
                    System.out.print("Enter the settlement date (yyyy-MM-dd): ");
                    settlementDate = LocalDate.parse(scanner.nextLine(), formatter);

                    System.out.print("Enter the maturity date (yyyy-MM-dd): ");
                    maturityDate = LocalDate.parse(scanner.nextLine(), formatter);

                    System.out.print("Enter the last coupon date (yyyy-MM-dd): ");
                    lastCouponDate = LocalDate.parse(scanner.nextLine(), formatter);

                    System.out.print("Enter the coupon rate (as a decimal, e.g., 0.0825): ");
                    couponRate = Double.parseDouble(scanner.nextLine());

                    System.out.print("Enter the yield to maturity (as a decimal, e.g., 0.095): ");
                    yieldToMaturity = Double.parseDouble(scanner.nextLine());
                }
                default -> {
                    System.out.println("Invalid choice. Please enter '1', '2', or 'no'.");
                    return;
                }
            }
            double dayCountConvention = 365; // SAGB Day Count Convention = Actual/365
            int couponFrequency = 2; // SAGB coupons are paid semiannually

            Bond bond = new Bond(settlementDate, maturityDate, lastCouponDate, couponRate, yieldToMaturity, nominalValue,
                    dayCountConvention, couponFrequency);

            double cleanPrice = bond.calculateCleanPrice();
            double accruedInterest = bond.calculateAccruedInterest();
            double allInPrice = bond.calculateAllInPrice();

            String cleanPriceFormatted = String.format(Locale.US, "%.4f", cleanPrice);
            String accruedInterestFormatted = String.format(Locale.US, "%.4f", accruedInterest);
            String allInPriceFormatted = String.format(Locale.US, "%.4f", allInPrice);
            String nominalValueFormatted = String.format(Locale.US, "%.2f", nominalValue);

            System.out.printf("\nThe Clean Price of the SAGB with a nominal of R" + nominalValueFormatted + " is equal to R" + cleanPriceFormatted + "\n");
            System.out.printf("The Accrued Interest of the SAGB with a nominal of R" + nominalValueFormatted + " is equal to R" + accruedInterestFormatted + "\n");
            System.out.printf("The All-in-Price of the SAGB with a nominal of R" + nominalValueFormatted + " is equal to R" + allInPriceFormatted + "\n");
        }
    }
}
