package marketplace.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for item which needs to update.
 */
@Data
@NoArgsConstructor
public class ItemForUpdateDto {
    private long id;
    private String title;
    private String description;
    private float startPrice;
    private float bidInc;
    private String stopDate;
    private long userId;
    private String seller;

}
