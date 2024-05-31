
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.time.temporal.ChronoUnit;

public class YieldCurve {
    //Treemap maps the date objects to the rate objects in the Main Class
    private final TreeMap<LocalDate, Rate> rates;

    //This is the constructor to initialise the Yield Curve objects given the arrays dates, bid & ask rates
    public YieldCurve(String[] dates, double[] bidRates, double[] askRates) {
        //Checks if the input lengths are equal - if not an exception is thrown
        if (dates.length != bidRates.length || dates.length != askRates.length) {
            throw new IllegalArgumentException("All input arrays must have the same length.");
        }
        //Initialises the rates in a tree map
        rates = new TreeMap<>();
        //Declare a date formatter to parse dates strings in the format yyyy-MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //Iterates over thr arrays over dates, bid & ask rates
        for (int i = 0; i < dates.length; i++) {
            LocalDate date = LocalDate.parse(dates[i], formatter);
            rates.put(date, new Rate(bidRates[i], askRates[i]));
            //Creates a new Rate object with the corresponding bid and ask rates and puts it into the rates map.
        }
    }
    //getRate method for the given date and requested rate type
    public double getRate(LocalDate date, String rateType) {

        //If the exact date is within the map, return the given rate directly
        if (rates.containsKey(date)) {
            return getRateByType(rates.get(date), rateType);
        }
        //If the date input is not an exact date within the map, gets the entries of nearest date before and after the input date
        Map.Entry<LocalDate, Rate> lowerEntry = rates.lowerEntry(date);
        Map.Entry<LocalDate, Rate> higherEntry = rates.higherEntry(date);
        //Throws an error if the date is before the first date in the curve
        if (lowerEntry == null) {
            throw new IllegalArgumentException("Date is before the first available date.");
        }
        //If the input date is after the dates within the yield curve, retrieve the lastRate
        if (higherEntry == null) {
            Rate lastRate = rates.lastEntry().getValue();
            return getRateByType(lastRate, rateType);
        }

        LocalDate lowerDate = lowerEntry.getKey();
        LocalDate higherDate = higherEntry.getKey();
        Rate lowerRate = lowerEntry.getValue();
        Rate higherRate = higherEntry.getValue();
        //Interpolates the rate between the lower and higher entries and returns it.
        return interpolate(date, lowerDate, higherDate, lowerRate, higherRate, rateType);
    }

    //Helper method to get the rate value based on the rate type (bid, ask, or mid).
    private double getRateByType(Rate rate, String rateType) {
        return switch (rateType.toLowerCase()) {
            case "bid" -> rate.bidRate;
            case "ask" -> rate.askRate;
            case "mid" -> (rate.bidRate + rate.askRate) / 2;
            default -> throw new IllegalArgumentException("Rate type must be 'bid', 'ask', or 'mid'.");
        };
    }

    //Helper method to perform linear interpolation between two dates and rates.
    private double interpolate(LocalDate date, LocalDate lowerDate, LocalDate higherDate, Rate lowerRate, Rate higherRate, String rateType) {
        long totalDays = ChronoUnit.DAYS.between(lowerDate, higherDate); //calculates the total number of days between lower date and the higher date
        long daysToTarget = ChronoUnit.DAYS.between(lowerDate, date);
        double lowerRateValue = getRateByType(lowerRate, rateType);
        double higherRateValue = getRateByType(higherRate, rateType);

        //Calculation for linear interpolation: Rate = Rate(Lower date) + [(Input date - Lower date) x (higher rate - lower rate)/(sum of days between lower and higher date)]
        return lowerRateValue + (daysToTarget * (higherRateValue - lowerRateValue) / totalDays);

    }

    //Defines a nested static class Rate,
    private static class Rate {
        final double bidRate;
        final double askRate;

        Rate(double bidRate, double askRate) {
            this.bidRate = bidRate;
            this.askRate = askRate;
        }
    }
}