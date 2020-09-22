package marketplace.services.impl;

import marketplace.domain.ItemForUpdateDto;
import marketplace.domain.ResponseDto;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.persistence.ItemDao;
import marketplace.services.ItemService;
import marketplace.validation.ItemValidator;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static marketplace.constants.ApplicationConstants.*;

/**
 * Implementation of {@link ItemService}.
 */
public class ItemServiceImpl implements ItemService {

    @Inject
    private ItemDao itemDao;
    @Inject
    private ItemValidator validator;

    /**
     * Returns response in JSON. Response consists of list of items.
     *
     * @return ResponseDTO is a representation of list of items.
     */
    @Override
    public ResponseDto getAllItems() throws SQLException, IOException {
        return new ResponseDto(new ArrayList<>(itemDao.getAllItems()), false);
    }

    @Override
    public ResponseDto getCurrentUserItems(String email) throws SQLException, IOException {
        return new ResponseDto(new ArrayList<>(itemDao.getCurrentUserItems(email)), false);
    }

    /**
     * Find item by it's id.
     *
     * @param itemId is id of item which need to find.
     * @return found item.
     * @throws SQLException if occurred.
     */
    @Override
    public Item getItem(String itemId) throws SQLException, IOException {
        if (itemId == null)
            return null;
        final Optional<Item> itemById = itemDao.findItemById(Long.valueOf(itemId));
        return itemById.orElse(null);
    }

    /**
     * Method for editing or creating item.
     *
     * @param requestParameters item parameters received on server from user.
     * @param principal         is the user who editing item.
     */
    @Override
    public void editItem(Map<String, String[]> requestParameters, User principal)
            throws SQLException, IOException {
        validator.validate(requestParameters);
        final ItemForUpdateDto item = createItemForUpdateFromRequestMap(requestParameters, principal);
        if (item.getId() == 0) {
            itemDao.saveItem(item, principal.getId());
        } else {
            itemDao.editItem(item, principal.getId());
        }
    }

    @Override
    public String getDate(Item item) {
        final String[] splitDate = splitDate(item);
        return splitDate[0];
    }

    @Override
    public String getTime(Item item) {
        final String[] splitDate = splitDate(item);
        return splitDate[1];
    }

    private String[] splitDate(Item item) {
        final String stopDate = item.getStopDate();
        return stopDate.split(WHITE_SPACE);
    }

    private ItemForUpdateDto createItemForUpdateFromRequestMap(
            Map<String, String[]> requestParameters, User principal) {
        final ItemForUpdateDto item = new ItemForUpdateDto();
        defineIdAndSet(requestParameters, item);
        item.setTitle(requestParameters.get(TITLE)[0]);
        item.setUserId(principal.getId());
        item.setDescription(requestParameters.get(DESCRIPTION)[0]);
        setStartPriceAndBidIncrement(requestParameters, item);
        createDate(requestParameters, item);
        setSellerWithPrefix(item, principal);
        return item;
    }

    private void setSellerWithPrefix(ItemForUpdateDto item, User principal) {
        final String prefix = principal.getGender().getPrefix();
        item.setSeller(prefix + principal.getLastName());
    }

    private void defineIdAndSet(Map<String, String[]> requestParameters, ItemForUpdateDto item) {
        if (requestParameters.containsKey(ITEM_ID)) {
            item.setId(Long.parseLong(requestParameters.get(ITEM_ID)[0]));
        } else {
            item.setId(0L);
        }
    }

    private void setStartPriceAndBidIncrement(Map<String, String[]> requestParameters, ItemForUpdateDto item) {
        if (requestParameters.containsKey(START_PRICE_CAMEL_CASE)) {
            final BigDecimal startPrice = new BigDecimal(requestParameters.get(START_PRICE_CAMEL_CASE)[0])
                    .setScale(2, RoundingMode.CEILING);
            item.setStartPrice(startPrice.floatValue());
            final BigDecimal bidIncrement = new BigDecimal(requestParameters.get(BID_INCREMENT)[0])
                    .setScale(2, RoundingMode.CEILING);
            item.setBidInc(bidIncrement.floatValue());
        }
    }

    private void createDate(Map<String, String[]> requestParameters, ItemForUpdateDto item) {
        if (requestParameters.containsKey(STOP_DATE_CAMEL_CASE)) {
            final String stopDate = requestParameters.get(STOP_DATE_CAMEL_CASE)[0];
            final String stopTime = requestParameters.get(STOP_TIME_CAMEL_CASE)[0];
            final String finalDate = stopDate + " " + stopTime;
            item.setStopDate(finalDate);
        }
    }

}
