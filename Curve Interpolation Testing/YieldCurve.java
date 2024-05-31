import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;

public class YieldCurve {
    private final TreeMap<LocalDate, Rate> rates;

    public YieldCurve(String[] dates, double[] bidRates, double[] askRates) {
        if (dates.length == bidRates.length && dates.length == askRates.length) {
            this.rates = new TreeMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for(int i = 0; i < dates.length; ++i) {
                LocalDate date = LocalDate.parse(dates[i], formatter);
                this.rates.put(date, new Rate(bidRates[i], askRates[i]));
            }

        } else {
            throw new IllegalArgumentException("All input arrays must have the same length.");
        }
    }

    public double getRate(LocalDate date, String rateType) {
        if (this.rates.containsKey(date)) {
            return this.getRateByType(this.rates.get(date), rateType);
        } else {
            Map.Entry<LocalDate, Rate> lowerEntry = this.rates.lowerEntry(date);
            Map.Entry<LocalDate, Rate> higherEntry = this.rates.higherEntry(date);
            if (lowerEntry == null) {
                throw new IllegalArgumentException("Date is before the first available date.");
            } else if (higherEntry == null) {
                Rate lastRate = this.rates.lastEntry().getValue();
                return this.getRateByType(lastRate, rateType);
            } else {
                LocalDate lowerDate = lowerEntry.getKey();
                LocalDate higherDate = higherEntry.getKey();
                Rate lowerRate = lowerEntry.getValue();
                Rate higherRate = higherEntry.getValue();
                return this.interpolate(date, lowerDate, higherDate, lowerRate, higherRate, rateType);
            }
        }
    }

    private double getRateByType(Rate rate, String rateType) {
        double var10000;
        switch (rateType.toLowerCase()) {
            case "bid" -> var10000 = rate.bidRate;
            case "ask" -> var10000 = rate.askRate;
            case "mid" -> var10000 = (rate.bidRate + rate.askRate) / 2.0;
            default -> throw new IllegalArgumentException("Rate type must be 'bid', 'ask', or 'mid'.");
        }

        return var10000;
    }

    private double interpolate(LocalDate date, LocalDate lowerDate, LocalDate higherDate, Rate lowerRate, Rate higherRate, String rateType) {
        long totalDays = ChronoUnit.DAYS.between(lowerDate, higherDate);
        long daysToTarget = ChronoUnit.DAYS.between(lowerDate, date);
        double lowerRateValue = this.getRateByType(lowerRate, rateType);
        double higherRateValue = this.getRateByType(higherRate, rateType);
        return lowerRateValue + (double)daysToTarget * (higherRateValue - lowerRateValue) / (double)totalDays;
    }

    private static class Rate {
        final double bidRate;
        final double askRate;

        Rate(double bidRate, double askRate) {
            this.bidRate = bidRate;
            this.askRate = askRate;
        }
    }
}

