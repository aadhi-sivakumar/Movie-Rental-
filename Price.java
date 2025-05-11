// Strategy pattern for pricing movies / price hierarchy
abstract class Price 
{
    static final int REGULAR = 0;
    static final int NEW_RELEASE = 1;
    static final int CHILDRENS = 2;

    abstract int getPriceCode();
    abstract double getCharge(int daysRented);

    // Default implementation for frequent renter points
    int getFrequentRenterPoints(int daysRented)
    {
        return 1;
    }
}
