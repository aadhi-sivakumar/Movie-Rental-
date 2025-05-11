class RentalStatement 
{
    // Calculates the total amount owed by the customer for both rentals and purchases
    public double calculateTotalAmount(Customer customer) 
    {
        double totalAmount = 0;

        // Add charges for each rental
        for (Rental rental : customer.getRentals())
        {
            totalAmount += rental.getCharge();
        }

        // Add prices for each purchase
        for (SoldMovie purchase : customer.getPurchases())
        {
            totalAmount += purchase.getPurchasePrice();
        }

        return totalAmount;
    }

    // Generates a detailed text statement for a customer including rentals, purchases, total amount, points, and coupons
    public String generateTextStatement(Customer customer, double totalAmount, int totalPoints) 
    {
        StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");

        // Append rental movie details and charges
        for (Rental rental : customer.getRentals()) 
        {
            result.append("  ")
                  .append(rental.getMovie().getTitle())
                  .append(" (rented): $")
                  .append(rental.getCharge())
                  .append("\n");
        }

        // Append purchased movie details and prices
        for (SoldMovie sold : customer.getPurchases()) 
        {
            result.append("  ")
                  .append(sold.getMovie().getTitle())
                  .append(" (purchased): $")
                  .append(sold.getPurchasePrice())
                  .append("\n");
        }

        // Append summary information: total owed, points earned, and coupon balances
        result.append("Amount owed is ").append(totalAmount).append("\n")
              .append("You earned a total of ").append(totalPoints).append(" points\n")
              .append("You have ").append(customer.getFreeMovieRentalCoupons()).append(" free rental movie coupons\n")
              .append("You have ").append(customer.getFreeMoviePurchaseCoupons()).append(" free purchase movie coupons\n")
              .append("Remaining rental points: ").append(customer.getFrequentRenterPoints()).append("\n")
              .append("Remaining purchase points: ").append(customer.getFrequentPurchasePoints());

        return result.toString();
    }

    // Generates an XML-formatted statement summarizing rentals, purchases, and the total amount
    public String generateXMLStatement(Customer customer, double totalAmount) 
    {
        StringBuilder result = new StringBuilder();
        result.append("<customer>\n\t<name>")
              .append(customer.getName())
              .append("</name>\n");

        // Add rental movies in XML format
        for (Rental rental : customer.getRentals()) 
        {
            result.append("\t<movie type=\"rental\">\n")
                  .append("\t\t<title>")
                  .append(rental.getMovie().getTitle())
                  .append("</title>\n")
                  .append("\t\t<charge>")
                  .append(rental.getCharge())
                  .append("</charge>\n")
                  .append("\t</movie>\n");
        }

        // Add purchased movies in XML format
        for (SoldMovie sold : customer.getPurchases()) 
        {
            result.append("\t<movie type=\"purchase\">\n")
                  .append("\t\t<title>")
                  .append(sold.getMovie().getTitle())
                  .append("</title>\n")
                  .append("\t\t<charge>")
                  .append(sold.getPurchasePrice())
                  .append("</charge>\n")
                  .append("\t</movie>\n");
        }

        // Add total amount as XML element
        result.append("\t<total_amount>")
              .append(totalAmount)
              .append("</total_amount>\n");

        result.append("</customer>");
        return result.toString();
    }
}
