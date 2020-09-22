package marketplace.queries;

public class ItemQueriesConstants {

    public static final String SELECT_FROM_ITEM_BY_ID = "SELECT i.Id, i.Title, i.Description, i.Seller, i.Start_Price, i.Bid_Inc, i.Best_Offer, i.Bidder, i.Stop_Date, i.User_Id, u.Email FROM Item i JOIN Usr u ON i.User_Id = u.Id WHERE i.Id = ?";
    public static final String INSERT_INTO_ITEM = "INSERT INTO Item (Title, Description, Seller, Start_Price, Bid_Inc, Best_Offer, Bidder, Stop_Date, User_Id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ITEM = "UPDATE Item SET Title = ?, Description = ? WHERE Id = ?";
    public static final String SELECT_FROM_ITEM = "SELECT i.Id, i.Title, i.Description, i.Seller, i.Start_Price, i.Bid_Inc, i.Best_Offer, i.Bidder, i.Stop_Date, i.User_Id, u.Email FROM Item i JOIN Usr u ON i.User_Id = u.Id ORDER BY i.Id DESC";
    public static final String SELECT_FROM_ITEM_BY_USER_ID = "SELECT i.Id, i.Title, i.Description, i.Seller, i.Start_Price, i.Bid_Inc, i.Best_Offer, i.Bidder, i.Stop_Date, i.User_Id, u.Email FROM Item i JOIN Usr u ON i.User_Id = u.Id WHERE u.Email = ? ORDER BY i.Id DESC";
    public static final String UPDATE_ITEM_AFTER_BID = "UPDATE Item SET Best_Offer = ?, Bidder = ? WHERE Id = ?";

}
