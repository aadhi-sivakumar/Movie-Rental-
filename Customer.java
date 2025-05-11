import java.util.ArrayList;
import java.util.List;

public class Customer
{
    private String name;
    private List<Rental> rentals = new ArrayList<>();
    private List<SoldMovie> purchases = new ArrayList<>();
    public Coupon coupon = new NoCoupon(); // Start with no discount

    private int frequentRenterPoints = 0;
    private int frequentPurchasePoints = 0;
    private int freeRentalCoupons = 0;
    private int freePurchaseCoupons = 0;

    private int usedRentalCouponsThisOrder = 0;
    private int usedPurchaseCouponsThisOrder = 0;
    private int thisOrderRentalPoints = 0;
    private int thisOrderPurchasePoints = 0;

    public Customer(String name)
    {
        this.name = name;
    }

    // Add a rental and update renter points and coupons
    public void addRental(Rental rental)
    {
        rentals.add(rental);
        int points = rental.getFrequentRenterPoints();
        thisOrderRentalPoints += points;
        frequentRenterPoints += points;
        updateCoupons();
    }

    // Add a movie purchase and update purchase points and coupons
    public void addPurchase(SoldMovie soldMovie)
    {
        purchases.add(soldMovie);
        int points = soldMovie.getPurchasePoints();
        thisOrderPurchasePoints += points;
        frequentPurchasePoints += points;
        updateCoupons();
    }

    // Update coupon counts based on accumulated points
    private void updateCoupons()
    {
        int earnedRental = frequentRenterPoints / 10;
        int earnedPurchase = frequentPurchasePoints / 10;

        freeRentalCoupons += earnedRental;
        freePurchaseCoupons += earnedPurchase;

        frequentRenterPoints %= 10;
        frequentPurchasePoints %= 10;
    }

    // Apply a free rental coupon if available
    public void applyFreeMovieCoupon()
    {
        if (freeRentalCoupons > 0 && !rentals.isEmpty())
        {
            coupon = new FreeRentalCoupon(coupon);
            freeRentalCoupons--;
            usedRentalCouponsThisOrder++;
        }
    }

    // Apply a free purchase coupon if available
    public void applyFreePurchaseMovieCoupon()
    {
        if (freePurchaseCoupons > 0 && !purchases.isEmpty())
        {
            coupon = new FreePurchaseCoupon(coupon);
            freePurchaseCoupons--;
            usedPurchaseCouponsThisOrder++;
        }
    }

    // Add a discount coupon to the current stack of coupons
    public void addCoupon(Coupon newCoupon)
    {
        if (newCoupon instanceof NoCoupon || newCoupon instanceof FreeRentalCoupon || newCoupon instanceof FreePurchaseCoupon)
        {
            coupon = newCoupon; // Replace directly
        }
        else if (newCoupon instanceof HalfOffCoupon)
        {
            coupon = new HalfOffCoupon(coupon); // Wrap the existing coupon with HalfOff
        }
        else
        {
            coupon = new TenDollarOffCoupon(coupon); // Default to $10 off 
        }
    }

    // Generate full receipt text with total and coupon effects
    public String generateTextStatement()
    {
        double totalAmount = calculateTotalAmount();
        totalAmount = this.coupon.apply(this.getRentals(), this.getPurchases(), totalAmount);
        return generateStatement(totalAmount);
    }

    // Compute original total cost (before coupon)
    private double calculateTotalAmount()
    {
        double totalAmount = 0;

        for (Rental rental : rentals)
        {
            totalAmount += rental.getCharge();
        }

        for (SoldMovie soldMovie : purchases)
        {
            totalAmount += soldMovie.getPurchasePrice();
        }

        return totalAmount;
    }

    // Create formatted output statement showing rentals, purchases, totals, points, and coupons
    private String generateStatement(double totalAmount)
    {
        StringBuilder sb = new StringBuilder("Rental Record for: ").append(getName()).append("\n\n");

        for (Rental rental : rentals)
        {
            sb.append(" Rented - ").append(rental.getMovie().getTitle())
              .append(": $").append(String.format("%.2f", rental.getCharge())).append("\n");
        }

        for (SoldMovie soldMovie : purchases)
        {
            sb.append(" Purchased - ").append(soldMovie.getMovie().getTitle())
              .append(": $").append(String.format("%.2f", soldMovie.getPurchasePrice())).append("\n");
        }

        sb.append("\nAmount owed is: $").append(String.format("%.2f", totalAmount)).append("\n\n");

        sb.append("You earned: ")
          .append(thisOrderRentalPoints).append(" rental points and ")
          .append(thisOrderPurchasePoints).append(" purchase points in this order\n\n");

        sb.append("Free rental coupons used: ").append(usedRentalCouponsThisOrder).append("\n");
        sb.append("Free purchase coupons used: ").append(usedPurchaseCouponsThisOrder).append("\n\n");

        sb.append("Remaining rental points: ").append(frequentRenterPoints).append(" / 20\n");
        sb.append("Remaining purchase points: ").append(frequentPurchasePoints).append(" / 20\n");
        sb.append("Total free rental coupons: ").append(freeRentalCoupons).append("\n");
        sb.append("Total free purchase coupons: ").append(freePurchaseCoupons).append("\n");

        return sb.toString();
    }

    // Getters
    public String getName()
    {
        return name;
    }

    public List<Rental> getRentals()
    {
        return rentals;
    }

    public List<SoldMovie> getPurchases()
    {
        return purchases;
    }

    public int getFrequentRenterPoints()
    {
        return frequentRenterPoints;
    }

    public int getFrequentPurchasePoints()
    {
        return frequentPurchasePoints;
    }

    public int getFreeMovieRentalCoupons()
    {
        return freeRentalCoupons;
    }

    public int getFreeMoviePurchaseCoupons()
    {
        return freePurchaseCoupons;
    }

    // Clear rentals and purchases, and reset counters for current order
    public void clearRentals()
    {
        rentals.clear();
        purchases.clear();
        thisOrderRentalPoints = 0;
        thisOrderPurchasePoints = 0;
        usedRentalCouponsThisOrder = 0;
        usedPurchaseCouponsThisOrder = 0;
    }
}
