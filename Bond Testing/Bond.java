import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Bond {
    private LocalDate settlementDate;
    private LocalDate maturityDate;
    private LocalDate lastCouponDate;
    private double couponRate;
    private double yieldToMaturity;
    private double faceValue;
    private double dayCountConvention;
    private int couponFrequency;

    public Bond(LocalDate settlementDate, LocalDate maturityDate, LocalDate lastCouponDate,
                double couponRate, double yieldToMaturity, double faceValue,
                double dayCountConvention, int couponFrequency) {
        if (couponRate < 0 || yieldToMaturity < 0 || faceValue < 0) {
            throw new IllegalArgumentException("Coupon rate, yield, and face value must be non-negative.");
        }
        if (!settlementDate.isBefore(maturityDate)) {
            throw new IllegalArgumentException("Settlement date must be before maturity date.");
        }
        if (!lastCouponDate.isBefore(settlementDate) && !lastCouponDate.isEqual(settlementDate)) {
            throw new IllegalArgumentException("Last coupon date must be on or before settlement date.");
        }
        this.settlementDate = settlementDate;
        this.maturityDate = maturityDate;
        this.lastCouponDate = lastCouponDate;
        this.couponRate = couponRate;
        this.yieldToMaturity = yieldToMaturity;
        this.faceValue = faceValue;
        this.dayCountConvention = dayCountConvention;
        this.couponFrequency = couponFrequency;
    }

    public LocalDate getSettlementDate() {
        return this.settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public LocalDate getMaturityDate() {
        return this.maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public LocalDate getLastCouponDate() {
        return this.lastCouponDate;
    }

    public void setLastCouponDate(LocalDate lastCouponDate) {
        this.lastCouponDate = lastCouponDate;
    }

    public double getCouponRate() {
        return this.couponRate;
    }

    public void setCouponRate(double couponRate) {
        this.couponRate = couponRate;
    }

    public double getYieldToMaturity() {
        return this.yieldToMaturity;
    }

    public void setYield(double yieldToMaturity) {
        this.yieldToMaturity = yieldToMaturity;
    }

    public double getFaceValue() {
        return this.faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public double getDayCountConvention() {
        return this.dayCountConvention;
    }

    public void setDayCountConvention(double dayCountConvention) {
        this.dayCountConvention = dayCountConvention;
    }

    public int getCouponFrequency() {
        return this.couponFrequency;
    }

    public void setCouponFrequency(int couponFrequency) {
        this.couponFrequency = couponFrequency;
    }

    /**Function Objective:  Calculate Accrued Interest
     * The formula for accrued interest = Coupon Rate multitiplied by the the difference between the settlement date and the last coupon date over 365
     * To obtain the Rand value of the accrued interest multiply the above output by the nominal value of the bond */
    public double calculateAccruedInterest() {
        double daysSinceLastCoupon = ChronoUnit.DAYS.between(lastCouponDate, settlementDate);
        double accruedIncome = couponRate * (daysSinceLastCoupon / dayCountConvention) * faceValue;
        return accruedIncome;
    }

    /** Function Objective: Determine the Present Value of the Nominal
     * The Nominal is divided by the discount factor tp determine the present value
     * The coupon factor is raised to the number of coupon periods */
    public double calculatePresentValueOfFaceValue() {
        double daysToMaturity = ChronoUnit.DAYS.between(settlementDate, maturityDate);
        double pvFV = faceValue / Math.pow(1 + yieldToMaturity/ couponFrequency, daysToMaturity/dayCountConvention * couponFrequency);
        return pvFV;
    }

    /**Function Objective: Generate a list of all coupon dates in a list
     * SAGBs pay semi-annual coupon hence the coupon dates are 6 months apart
     * The loop is to ensure no coupon values are generated post the maturity date of the bond */
    public List<LocalDate> generateCouponDates() {
        List<LocalDate> couponDates = new ArrayList<>();
        LocalDate nextCouponDate = lastCouponDate.plusMonths(12 / 2);
        while (!nextCouponDate.isAfter(maturityDate)) {
            couponDates.add(nextCouponDate);
            nextCouponDate = nextCouponDate.plusMonths(12 / 2);
        }
        return couponDates;
    }

    /**Function Objective: All Coupons payments need to be discounted to their Present Value
     * The Present value is set initially set to 0 - once all coupons are discounted the variable is used to add all PV of coupons
     * Semi-annual coupons are equal to the Coupon Rate divided by 2 multiplied by the nominal of the bond */
    public double calculatePresentValueOfCoupons() {
        double semiAnnualCouponPayment = (couponRate / 2) * faceValue;
        double totalPV = 0.0;
        List<LocalDate> couponDates = generateCouponDates();
        for (LocalDate couponDate : couponDates) {
            long daysBetween = ChronoUnit.DAYS.between(settlementDate, couponDate);
            double pv = semiAnnualCouponPayment / Math.pow(1 + yieldToMaturity / couponFrequency, daysBetween / dayCountConvention * couponFrequency);
            totalPV += pv;
        }
        return totalPV;
    }

    /* Function Objective: Calculate the dirty price/all-in-price of the bond of the bond
     * The dirty price is the sum of the PV of coupons plus PV FaceValue/Nominal */

    public double calculateAllInPrice() {
        double presentValueOfFaceValue = calculatePresentValueOfFaceValue();
        double presentValueOfCoupons = calculatePresentValueOfCoupons();
        return presentValueOfCoupons + presentValueOfFaceValue;
    }
    /* Function Objective: Calculate the dirty price/all-in-price of the bond of the bond
     * The dirty price is the sum of the PV of coupons plus PV FaceValue/Nominal minus Accrued Interest */
    public double calculateCleanPrice() {
        double presentValueOfFaceValue = calculatePresentValueOfFaceValue();
        double presentValueOfCoupons = calculatePresentValueOfCoupons();
        double accruedInterest = calculateAccruedInterest();
        return presentValueOfFaceValue + presentValueOfCoupons - accruedInterest;
    }
}