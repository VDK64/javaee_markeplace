package marketplace.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for database store. Represents Item.
 */
@Data
@NoArgsConstructor
public class Item {
    private long id;
    private String title;
    private String description;
    private String seller;
    private float startPrice;
    private float bidInc;
    private float bestOffer;
    private String bidder;
    private String stopDate;
    private long userId;
    private String email;

}
