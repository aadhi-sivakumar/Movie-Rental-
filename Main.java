public class Main
{
    public static void main(String[] args) 
    {
        Movie m1 = new Movie("The Matrix", Price.REGULAR); 
        Movie m2 = new Movie("Avengers: Endgame", Price.NEW_RELEASE);
        Movie m3 = new Movie("Titanic", Price.CHILDRENS);

        // Test A: 2 purchases, no coupons
        Customer mark = new Customer("Mark");
        mark.addPurchase(new SoldMovie(m1)); // $20 + 2 purchae points 
        mark.addPurchase(new SoldMovie(m3)); // $15 + 2 purchase points
        // Total: $35 | Purchase Points: 4
        System.out.println("\n--- Test A: Purchases Only (No Coupons) ---\n" + mark.generateTextStatement());

        // Test B: 2 purchases, 50% off coupon
        Customer nina = new Customer("Nina");
        nina.addPurchase(new SoldMovie(m1)); // $20 + 2 purchase points
        nina.addPurchase(new SoldMovie(m2)); // $25 + 2 purchase points
        nina.addCoupon(new HalfOffCoupon(new NoCoupon()));
        // Total: $45 -> $22.50 | Purchase Points: 4
        System.out.println("\n--- Test B: Purchases with 50% Off Coupon ---\n" + nina.generateTextStatement());

        // Test C: 3 purchases, $10 off coupon
        Customer oliver = new Customer("Oliver");
        oliver.addPurchase(new SoldMovie(m1)); // $20 + 2 purchase points
        oliver.addPurchase(new SoldMovie(m1)); // $20 + 2 purchase points
        oliver.addPurchase(new SoldMovie(m2)); // $25 + 2 purchase points
        oliver.addCoupon(new TenDollarOffCoupon(new NoCoupon()));
        // Total: $65 -> $55 | Purchase Points: 6
        System.out.println("\n--- Test C: Purchases with $10 Off Coupon ---\n" + oliver.generateTextStatement());

        // Test D: apply 1 free purchase coupon
        Customer paula = new Customer("Paula");
        for (int i = 0; i < 10; i++) 
        {
            paula.addPurchase(new SoldMovie(m3)); // $15 x 10 = $150, 2 purchase points each = 20 purchase points
        }
        paula.applyFreePurchaseMovieCoupon(); 
        // Total: $150 | Purchase Points: 0 + 1 free purchase coupon available
        System.out.println("\n--- Test D: Earn + Apply Free Purchase Coupon ---\n" + paula.generateTextStatement());

        // Test E: Rentals with 50% off coupon
        Customer sam = new Customer("Sam");
        sam.addRental(new Rental(m1, 4)); // $5 + 1 rental point
        sam.addRental(new Rental(m2, 3)); // $9 + 2 rental points
        sam.addCoupon(new HalfOffCoupon(new NoCoupon()));
        // Total: $14.00 -> $7.00 | Rental Points: 3
        System.out.println("\n--- Test E: Rentals with 50% Off Coupon ---\n" + sam.generateTextStatement());

        // Test F: Rentals with $10 off coupon
        Customer tina = new Customer("Tina");
        for (int i = 0; i < 20; i++) {
            tina.addRental(new Rental(m2, 3)); // $9 each = $180, 2 rental points each = 40 rental points
        }
        tina.addCoupon(new TenDollarOffCoupon(new NoCoupon()));
        // Total: $180 -> $170 | Rental Points: 0 + 4 free rental coupons earned
        System.out.println("\n--- Test F: Rentals with $10 Off Coupon ---\n" + tina.generateTextStatement());

        // Test G: Earn 1 free rental coupon and apply it
        Customer uma = new Customer("Uma");
        for (int i = 0; i < 20; i++) 
        {
            uma.addRental(new Rental(m2, 1)); // $3 x 10 = $60, 1 rental point each = 20 rental points
        }
        uma.applyFreeMovieCoupon(); 
        // Total: $60 | Rental Points: 0 + 1 free rental coupon available
        System.out.println("\n--- Test G: Earn + Apply Free Rental Coupon ---\n" + uma.generateTextStatement());

        // Test H: Earn both free rental and purchase coupon, apply both
        Customer vince = new Customer("Vince");
        for (int i = 0; i < 20; i++)
        {
            vince.addRental(new Rental(m3, 1)); // $1.50 x 20 = $30, 1 rental point each = 20 pts
        }

        for (int i = 0; i < 10; i++)
        {
            vince.addPurchase(new SoldMovie(m1)); // $20 x10 = $200, 2 pts each = 20 pts 
        }
        vince.applyFreeMovieCoupon();         
        vince.applyFreePurchaseMovieCoupon(); 

        // Total: $30 + $200 = $230 → $208.50 |Rental and Purchase Points: 0 + 1 free rental coupon + 1 free purchase coupon available
        System.out.println("\n--- Test H: Apply Both Free Coupons ---\n" + vince.generateTextStatement());
    }
}
