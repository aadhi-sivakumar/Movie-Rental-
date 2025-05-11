public class Movie 
{
    private String title;
    private Price price; // Strategy reference

    public Movie(String title, int priceCode) 
    {
        this.title = title;
        setPriceCode(priceCode);
    }

    public int getPriceCode() 
    {
        return price.getPriceCode();
    }
    
    // Sets the appropriate pricing strategy based on the price code
    public void setPriceCode(int priceCode) 
    {
        switch (priceCode) 
        {
            case Price.REGULAR:
                price = new RegularPrice();
                break;
            case Price.NEW_RELEASE:
                price = new NewReleasePrice();
                break;
            case Price.CHILDRENS:
                price = new ChildrenPrice();
                break;
            default:
                // Throws an error if an invalid price code is passed
                throw new IllegalArgumentException("Invalid price code");
        }
    }

    public String getTitle() 
    {
        return title;
    }
    
    // Delegates the charge calculation to the appropriate pricing strategy
    public double getCharge(int daysRented) 
    {
        return price.getCharge(daysRented);
    }

    public int getFrequentRenterPoints(int daysRented) 
    {
        return price.getFrequentRenterPoints(daysRented);
    }
}
