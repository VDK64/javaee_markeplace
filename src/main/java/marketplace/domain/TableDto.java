package marketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * It is an data transfer object representing the item.
 */
@AllArgsConstructor
@Getter
public class TableDto {
    private final long id;
    private final String title;
    private final String description;
    private final String seller;
    private final float startPrice;
    private final float bidInc;
    private final float bestOffer;
    private final String bidder;
    private final String stopDate;

}
