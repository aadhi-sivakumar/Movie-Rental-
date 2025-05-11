import java.util.Comparator;
import java.util.List;

// Coupon interface defines a method to apply a discount on a given total amount
interface Coupon 
{
    double apply(List<Rental> rentals, List<SoldMovie> purchases, double totalAmount);
}

// Abstract decorator class for Coupon interface to allow flexible composition of multiple coupons
abstract class CouponDecorator implements Coupon 
{
    protected Coupon coupon; // wrapped coupon (can be another decorator or base NoCoupon)

    public CouponDecorator(Coupon coupon) 
    {
        this.coupon = coupon;
    }

    // Delegates apply call to the wrapped coupon
    public double apply(List<Rental> rentals, List<SoldMovie> purchases, double totalAmount) 
    {
        return coupon.apply(rentals, purchases, totalAmount);
    }
}

// Applies a 50% discount on the total amount after applying other coupons
class HalfOffCoupon extends CouponDecorator 
{
    public HalfOffCoupon(Coupon coupon) 
    {
        super(coupon);
    }

    @Override
    public double apply(List<Rental> rentals, List<SoldMovie> purchases, double totalAmount) 
    {
        totalAmount = super.apply(rentals, purchases, totalAmount); // apply inner coupon
        return totalAmount * 0.5; // apply 50% off
    }
}

// Applies $10 off if the total amount is greater than $50 after applying other coupons
class TenDollarOffCoupon extends CouponDecorator 
{
    public TenDollarOffCoupon(Coupon coupon) 
    {
        super(coupon);
    }

    @Override
    public double apply(List<Rental> rentals, List<SoldMovie> purchases, double totalAmount) 
    {
        totalAmount = super.apply(rentals, purchases, totalAmount); // apply inner coupon
        if (totalAmount > 50) 
        {
            return Math.max(0, totalAmount - 10); // subtract $10 if eligible
        } 
        else 
        {
            return totalAmount; // no discount if not eligible
        }
    }
}

// Makes the cheapest rental free by subtracting its charge from the total amount
class FreeRentalCoupon extends CouponDecorator 
{
    public FreeRentalCoupon(Coupon coupon) 
    {
        super(coupon);
    }

    @Override
    public double apply(List<Rental> rentals, List<SoldMovie> purchases, double totalAmount) 
    {
        totalAmount = super.apply(rentals, purchases, totalAmount); // apply inner coupon
        if (!rentals.isEmpty()) 
        {
            // Sort rentals by price in ascending order and subtract the cheapest one
            rentals.sort(Comparator.comparingDouble(Rental::getCharge));
            totalAmount -= rentals.get(0).getCharge();
        }
        return Math.max(0, totalAmount); // prevent negative total
    }
}

// Makes the cheapest purchase free by subtracting its price from the total amount
class FreePurchaseCoupon extends CouponDecorator 
{
    public FreePurchaseCoupon(Coupon coupon) 
    {
        super(coupon);
    }

    @Override
    public double apply(List<Rental> rentals, List<SoldMovie> purchases, double totalAmount) 
    {
        totalAmount = super.apply(rentals, purchases, totalAmount); // apply inner coupon
        if (!purchases.isEmpty()) 
        {
            // Sort purchases by price in ascending order and subtract the cheapest one
            purchases.sort(Comparator.comparingDouble(SoldMovie::getPurchasePrice));
            totalAmount -= purchases.get(0).getPurchasePrice();
        }
        return Math.max(0, totalAmount); // prevent negative total
    }
}

// Base case for coupons; represents no discount applied
class NoCoupon implements Coupon 
{
    public double apply(List<Rental> rentals, List<SoldMovie> purchases, double totalAmount) 
    {
        return totalAmount; // no changes to amount
    }
}
