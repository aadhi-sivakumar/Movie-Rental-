// Uses strategy pattern for pricing movies
// This class defines the pricing strategy for regular movies
class RegularPrice extends Price 
{
    int getPriceCode() 
    {
        return REGULAR;
    }

    // Calculates the rental charge for a regular movie
    // Base price is $2 for up to 2 days
    // After 2 days, it's $1.50 per additional day
    double getCharge(int daysRented)
     {
        double amount;
        if (daysRented > 2) 
        {
            amount = 2 + (daysRented - 2) * 1.5;
        } 
        else 
        {
            amount = 2;
        }
        return amount;
    }
}
