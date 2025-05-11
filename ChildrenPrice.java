// Uses strategy pattern for pricing movies
// This class defines the pricing strategy for children's movies
class ChildrenPrice extends Price 
{
    int getPriceCode() 
    {
        return CHILDRENS;
    }

    // Calculates the rental charge based on the number of days rented
    double getCharge(int daysRented) 
    {
        double amount;
        // If the movie is rented for more than 3 days, calculate extra charges
        if (daysRented > 3) 
        {
             // Base charge is 1.50 for the first 3 days, then 1.50 per extra day
            amount = 1.5 + (daysRented - 3) * 1.5;
        } 
        else 
        {
            // Flat rate charge for up to 3 days
            amount = 1.5;
        }
        return amount;
    }
}
