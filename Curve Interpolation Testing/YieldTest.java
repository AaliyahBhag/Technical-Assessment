import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.LocalDate.parse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;



class YieldCurveTest {
    private static YieldCurve yieldCurve;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    YieldCurveTest() {
    }

    @BeforeAll
    static void setup() {
        String[] dates = new String[]{"2024-05-17", "2024-08-15", "2024-11-13", "2025-02-11", "2025-05-12", "2025-08-10", "2025-11-08", "2026-02-06", "2026-05-07"};
        double[] bidRates = new double[]{4.5, 5.0, 6.0, 7.2, 7.6, 8.1, 9.0, 10.0, 11.3};
        double[] askRates = new double[]{4.55, 5.05, 6.05, 7.25, 7.65, 8.15, 9.05, 10.05, 11.35};
        yieldCurve = new YieldCurve(dates, bidRates, askRates);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    /** Objective of test:
     * if the exact date on the curve is the input by the user, the output is the point on the rate on the curve
     */
    @Test
    void testExactDateMatch() {
        LocalDate date = parse("2024-08-15", formatter);
        Assertions.assertEquals(5.0, yieldCurve.getRate(date, "bid"), 0.01);
        Assertions.assertEquals(5.05, yieldCurve.getRate(date, "ask"), 0.01);
        Assertions.assertEquals(5.025, yieldCurve.getRate(date, "mid"), 0.01);
    }

    /** Interpolation Test
     * This test is vital to ensure the interpolated output rate is correct for an input date.
     * It expected rate for 2024-10-01 for the bid, mid and ask rates are manually calculated and the code is run to validate the output is correct
     */
    @Test
    void testInterpolation() {
        LocalDate date = parse("2024-10-01", formatter);
        Assertions.assertEquals(5.52, yieldCurve.getRate(date, "bid"), 0.01);
        Assertions.assertEquals(5.57, yieldCurve.getRate(date, "ask"), 0.01);
        Assertions.assertEquals(5.55, yieldCurve.getRate(date, "mid"), 0.01);
    }

    /**  Objective of the test:
     * If the input date is before the dates available on the curve an illegal exception is thrown
     **/
     @Test
        void testDateBeforeFirstAvailableDate() {
        LocalDate date = parse("2024-01-01", formatter);
        Executable executable = () -> yieldCurve.getRate(date, "bid");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, executable);
        Assertions.assertEquals("Date is before the first available date.", exception.getMessage());
    }

    /**Objective of the test:
     * When the input date is after the dates along the curve, a flat extrapolate rate is used
     * Hence, the last rate on the curve is the output.
     */
    @Test
    void testDateAfterLastAvailableDate() {
        LocalDate date = parse("2027-01-01", formatter);
        Assertions.assertEquals(11.3, yieldCurve.getRate(date, "bid"), 0.01);
        Assertions.assertEquals(11.35, yieldCurve.getRate(date, "ask"), 0.01);
        Assertions.assertEquals(11.33, yieldCurve.getRate(date, "mid"), 0.01);
    }

    /**Objective of the test:
     *  there are two inputs for the user, the date and the rate type.
     * If the input rate type is not bid, ask or mid an illegal argument is thrown
     */
    @Test
    void testInvalidRateType() {
        LocalDate date = parse("2024-08-15", formatter);
        Executable executable = () -> yieldCurve.getRate(date, "no");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, executable);
        Assertions.assertEquals("Rate type must be 'bid', 'ask', or 'mid'.", exception.getMessage());
    }


}

