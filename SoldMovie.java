public class SoldMovie 
{
    private Movie movie;
    private boolean purchased;

    public SoldMovie(Movie movie) 
    {
        this.movie = movie;
        this.purchased = true;
    }

    public Movie getMovie() 
    {
        return movie;
    }
    // Sold movie prices
    public double getPurchasePrice() 
    {
        switch (movie.getPriceCode()) 
        {
            case Price.CHILDRENS:
                return 15;
            case Price.REGULAR:
                return 20;
            case Price.NEW_RELEASE:
                return 25;
            default:
                throw new IllegalArgumentException("Invalid price code");
        }
    }
    // Two purchase points per movie
    public int getPurchasePoints() 
    {
        return 2;
    }
}