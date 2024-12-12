package store.service;

import store.dto.OrderedItemDTO;
import store.dto.ReceiptDTO;
import store.model.receipt.Receipt;
import store.model.item.PurchasedItem;
import store.repository.CurrentItemRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptService {

    public ReceiptDTO getReceipt(List<OrderedItemDTO> orderedItems, boolean hasMembership){
        Receipt receipt = new Receipt();
        updatePurchasedItem(receipt, orderedItems);
        updateFreeItems(receipt, orderedItems);
        receipt.updateTotalPrice(hasMembership);
        return receipt.getReceipt();
    }

    private void updatePurchasedItem(Receipt receipt, List<OrderedItemDTO> orderedItems){
        List<PurchasedItem> purchasedItems = new ArrayList<>();
        for (OrderedItemDTO orderedItem : orderedItems){
            long itemPrice = CurrentItemRepository.getPriceByItemName(orderedItem.name());
            purchasedItems.add(new PurchasedItem(orderedItem.name(), orderedItem.quantity(), itemPrice));
            decreaseItemQuantity(orderedItem.name(), orderedItem.quantity());
        }
        receipt.setPurchasedItems(purchasedItems);
    }

    private void decreaseItemQuantity(String itemName, long quantity){
        CurrentItemRepository.decreaseItemQuantity(itemName, quantity);
    }

    private void updateFreeItems(Receipt receipt, List<OrderedItemDTO> orderedItems){
        Map<String, Long> freeItems = new HashMap<>();
        for (OrderedItemDTO orderedItem : orderedItems){
            if (CurrentItemRepository.hasItem(orderedItem.name())){
                long freeQuantity = CurrentItemRepository.getFreeQuantity(orderedItem.name(), orderedItem.quantity());
                if (freeQuantity > 0){
                    freeItems.put(orderedItem.name(), freeQuantity);
                }
            }
        }
        receipt.setFreeItems(freeItems);
    }

}
