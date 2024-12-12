package store.service;

import store.dto.OrderedItemDTO;
import store.exception.ErrorMessages;
import store.repository.CurrentItemRepository;
import store.validator.ItemInputValidator;

import java.util.ArrayList;
import java.util.List;

public class OrderedItemRegisterService {

    private static final String EACH_ITEM_DELIMITER = ",";
    private static final String ITEM_INFORMATION_DELIMITER = "-";
    private static final String OPEN_SQUARE_BRACKETS = "\\[";
    private static final String CLOSE_SQUARE_BRACKETS = "\\]";

    public List<OrderedItemDTO> registerOrderedItemDTO(String input){
        List<String> rawItems = List.of(input.split(EACH_ITEM_DELIMITER));
        for (String rawItem : rawItems) {
            ItemInputValidator.validateItemInput(rawItem.trim());
        }
        return registerItems(rawItems);
    }

    private List<OrderedItemDTO> registerItems(List<String> rawItems){
        List<OrderedItemDTO> DTOS = new ArrayList<>();
        for (String rawItem : rawItems) {
            rawItem = rawItem.replace(OPEN_SQUARE_BRACKETS, "").replace(CLOSE_SQUARE_BRACKETS, "");
            List<String> itemInformation = List.of(rawItem.split(ITEM_INFORMATION_DELIMITER));
            validateItemExistence(itemInformation.get(0).trim());
            validateItemQuantity(itemInformation.get(0).trim(), Long.parseLong(itemInformation.get(1).trim()));
            DTOS.add(new OrderedItemDTO(itemInformation.get(0).trim(), Long.parseLong(itemInformation.get(1).trim())));
        }
        return DTOS;
    }

    private void validateItemExistence(String itemName){
        if (!CurrentItemRepository.hasItem(itemName)){
            throw new IllegalArgumentException(ErrorMessages.ITEM_NOT_EXIST.getMessage());
        }
    }

    private void validateItemQuantity(String itemName, long quantityToBuy){
        if(!CurrentItemRepository.hasEnoughQuantityToBuy(itemName, quantityToBuy)){
            throw new IllegalArgumentException(ErrorMessages.EXCEED_ITEM_QUANTITY.getMessage());
        }
    }
}
