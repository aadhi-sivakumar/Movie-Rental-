// Uses strategy pattern for pricing movies
// This class defines the pricing strategy for New Release movies
class NewReleasePrice extends Price 
{
    int getPriceCode() 
    {
        return NEW_RELEASE;
    }
    
    // Calculates the charge for renting a new release
    // Each day costs $3
    double getCharge(int daysRented) 
    {
        return daysRented * 3;
    }
    
    // Calculates frequent renter points for a new release rental
    // If the movie is rented for more than 1 day, award 2 points. Otherwise, award 1 point
    // This is an override of the default implementation in the Price class
    int getFrequentRenterPoints(int daysRented) 
    {
        int points;
        if (daysRented > 1)
        {
            points = 2;
        } 
        else 
        {
            points = 1;
        }
        return points;
    }
}
