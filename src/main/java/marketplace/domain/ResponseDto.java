package marketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import marketplace.entities.Item;

import java.util.List;

/**
 * This is a data transfer object class to store and delivery data to frontend.
 */
@AllArgsConstructor
@Getter
public class ResponseDto {
    /**
     * List of tables where table is an object which represents item.
     */
    private final List<Item> tables;
    private final boolean error;

}
