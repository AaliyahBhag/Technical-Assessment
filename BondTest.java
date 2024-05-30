import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

class BondTest {
    public Bond bond;

    @BeforeEach
    void setUp() {
        LocalDate settlementDate = LocalDate.parse("2024-05-16");
        LocalDate maturityDate = LocalDate.parse("2032-03-31");
        LocalDate lastCouponDate = LocalDate.parse("2024-03-31");
        double couponRate = 0.0825;
        double yieldToMaturity = 0.095;
        double nominalValue = 100.00;
        double dayCountConvention = 365;
        int couponFrequency = 2;

        bond = new Bond(settlementDate, maturityDate, lastCouponDate, couponRate, yieldToMaturity, nominalValue, dayCountConvention, couponFrequency);
    }

    @Test //Ensures that the bond object is properly initialized and not null after the setup
    void testInitialization() {
        assertNotNull(bond);
    }

    @Test //Verifies that the constructor correctly throws an IllegalArgumentException when negative values are provided for coupon rate, yield, or face value.
    void testNegativeValues() {
        LocalDate settlementDate = LocalDate.parse("2024-05-16");
        LocalDate maturityDate = LocalDate.parse("2032-03-31");
        LocalDate lastCouponDate = LocalDate.parse("2024-03-31");

        assertThrows(IllegalArgumentException.class,
                () -> new Bond(settlementDate, maturityDate, lastCouponDate, -0.0825, 0.095, 100.00, 365, 2)
        );

        assertThrows(IllegalArgumentException.class,
                () -> new Bond(settlementDate, maturityDate, lastCouponDate, 0.0825, -0.095, 100.00, 365, 2)
        );

        assertThrows(IllegalArgumentException.class,
                () -> new Bond(settlementDate, maturityDate, lastCouponDate, 0.0825, 0.095, -100.00, 365, 2)
        );
    }

    @Test //Tests if an IllegalArgumentException is thrown when the settlement date is after the maturity date.
    void testSettlementBeforeMaturity() {
        LocalDate settlementDate = LocalDate.parse("2032-05-16");
        LocalDate maturityDate = LocalDate.parse("2032-03-31");
        LocalDate lastCouponDate = LocalDate.parse("2024-03-31");

        assertThrows(IllegalArgumentException.class,
                () -> new Bond(settlementDate, maturityDate, lastCouponDate, 0.0825, 0.095, 100.00, 365, 2)
        );
    }

    @Test //Checks if an IllegalArgumentException is thrown when the last coupon date is after the settlement date.
    void testLastCouponBeforeOrEqualToSettlement() {
        LocalDate settlementDate = LocalDate.parse("2024-05-16");
        LocalDate maturityDate = LocalDate.parse("2032-03-31");
        LocalDate lastCouponDate = LocalDate.parse("2024-06-01");

        assertThrows(IllegalArgumentException.class,
                () -> new Bond(settlementDate, maturityDate, lastCouponDate, 0.0825, 0.095, 100.00, 365, 2)
        );
    }

    @Test // Checks if the accrued interest is calculated correctly and is non-negative.
    void testCalculateAccruedInterest() {
        double expectedAccruedInterest = bond.calculateAccruedInterest();
        assertTrue(expectedAccruedInterest >= 0, "Accrued interest should be non-negative.");
    }

    @Test //Verifies that the present value of the face value is calculated correctly and is greater than zero
    void testCalculatePresentValueOfFaceValue() {
        double expectedPVFaceValue = bond.calculatePresentValueOfFaceValue();
        assertTrue(expectedPVFaceValue > 0, "Present value of face value should be greater than zero.");
    }

    @Test // Ensures that the list of generated coupon dates is not empty and that the last coupon date is on or before the maturity date
    void testGenerateCouponDates() {
        List<LocalDate> couponDates = bond.generateCouponDates();
        assertFalse(couponDates.isEmpty(), "Coupon dates should not be empty.");
        assertTrue(couponDates.get(couponDates.size() - 1).isBefore(bond.getMaturityDate()) || couponDates.get(couponDates.size() - 1).isEqual(bond.getMaturityDate()), "Last coupon date should be on or before the maturity date.");
    }

    @Test //Verifies that the present value of coupons is calculated correctly and is greater than zero
    void testCalculatePresentValueOfCoupons() {
        double expectedPVCoupons = bond.calculatePresentValueOfCoupons();
        assertTrue(expectedPVCoupons > 0, "Present value of coupons should be greater than zero.");
    }

    @Test // Tests if the dirty price is calculated correctly and is greater than zero.
    void testCalculateAllInPrice() {
        double expectedDirtyPrice = bond.calculateAllInPrice();
        assertTrue(expectedDirtyPrice > 0, "Dirty price should be greater than zero.");
    }

    @Test //: Checks if the clean price is calculated correctly and is greater than zero
    void testCalculateCleanPrice() {
        double expectedCleanPrice = bond.calculateCleanPrice();
        assertTrue(expectedCleanPrice > 0, "Clean price should be greater than zero.");
    }

    @Test //When a bond is trading at a premium = YTM < Coupon the Clean & Dirty Price must be greater than the Nominal of the bond
    void testYieldLessThanCouponRate() {
        LocalDate settlementDate = LocalDate.parse("2024-05-16");
        LocalDate maturityDate = LocalDate.parse("2032-03-31");
        LocalDate lastCouponDate = LocalDate.parse("2024-03-31");
        double couponRate = 0.10;
        double yieldToMaturity = 0.05; // Yield is less than coupon rate
        double nominalValue = 100.00;
        double dayCountConvention = 365;
        int couponFrequency = 2;

        Bond bondWithLowerYield = new Bond(settlementDate, maturityDate, lastCouponDate, couponRate, yieldToMaturity, nominalValue, dayCountConvention, couponFrequency);

        double cleanPrice = bondWithLowerYield.calculateCleanPrice();
        double allInPrice = bondWithLowerYield.calculateAllInPrice();

        assertTrue(cleanPrice > nominalValue, "Clean price should be greater than face value when yield is less than coupon rate.");
        assertTrue(allInPrice > nominalValue, "Dirty price should be greater than face value when yield is less than coupon rate.");
    }

    @Test //When a bond is trading at a discount: YTM > Coupon Rate the Clean and Dirty Price is less than the Nominal of the bond
    void testYieldGreaterThanCouponRate() {
        LocalDate settlementDate = LocalDate.parse("2024-05-16");
        LocalDate maturityDate = LocalDate.parse("2032-03-31");
        LocalDate lastCouponDate = LocalDate.parse("2024-03-31");
        double couponRate = 0.05;
        double yieldToMaturity = 0.10; // Yield is greater than coupon rate
        double nominalValue = 100.00;
        double dayCountConvention = 365;
        int couponFrequency = 2;

        Bond bondWithHigherYield = new Bond(settlementDate, maturityDate, lastCouponDate, couponRate, yieldToMaturity, nominalValue, dayCountConvention, couponFrequency);

        double cleanPrice = bondWithHigherYield.calculateCleanPrice();
        double allInPrice= bondWithHigherYield.calculateAllInPrice();

        assertTrue(cleanPrice < nominalValue, "Clean price should be less than face value when yield is greater than coupon rate.");
        assertTrue(allInPrice < nominalValue, "Dirty price should be less than face value when yield is greater than coupon rate.");
    }
}

