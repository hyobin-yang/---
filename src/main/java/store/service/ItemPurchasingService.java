package store.service;

import store.dto.OrderedItemDTO;
import store.repository.CurrentItemRepository;

public class ItemPurchasingService {

    public boolean canBringMoreFreeItem(OrderedItemDTO orderedItem){
        if (!CurrentItemRepository.hasPromotion(orderedItem.name())){
            return false;
        }
        return CurrentItemRepository.canBringMoreFreeItem(orderedItem.name(), orderedItem.quantity());
    }

    public void bringMoreFreeItem(OrderedItemDTO orderedItem){
        orderedItem.increaseQuantity(1);
    }

    public boolean hasEnoughPromotionQuantity(OrderedItemDTO orderedItem){
        if (CurrentItemRepository.hasPromotion(orderedItem.name())){
            return CurrentItemRepository.hasEnoughPromotionQuantity(orderedItem.name(), orderedItem.quantity());
        }
        return true;
    }

    public long getInsufficientQuantity(OrderedItemDTO orderedItem){
        return CurrentItemRepository.getInsufficientQuantity(orderedItem.name(), orderedItem.quantity());
    }

}
