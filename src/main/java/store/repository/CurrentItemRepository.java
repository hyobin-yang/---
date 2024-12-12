package store.repository;

import store.model.item.Item;
import store.model.policy.Promotion;

import java.util.ArrayList;
import java.util.List;

public class CurrentItemRepository {

    private static final List<Item> items = new ArrayList<>();

    public static void initializeItems(){
        items.clear();
    }

    public static boolean hasItem(String itemName){
        for (Item item : items){
            if (item.isSameItemName(itemName)){
                return true;
            }
        }
        return false;
    }

    private static Item getItemByName(String name){
        for (Item item : items){
            if (item.isSameItemName(name)){
                return item;
            }
        }
        throw new IllegalArgumentException("[SYSTEM] 존재하지 않는 상품입니다. 시스템을 확인해주세요.");
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void increaseGeneralQuantity(String itemName, long quantity){
        for (Item item : items) {
            if (item.isSameItemName(itemName)){
                item.increaseGeneralQuantity(quantity);
            }
        }
    }

    public void increasePromotionQuantity(String itemName, long quantity){
        for (Item item : items) {
            if (item.isSameItemName(itemName)){
                item.increasePromotionQuantity(quantity);
            }
        }
    }

    public void addPromotion(String itemName, Promotion promotion){
        for (Item item : items) {
            if (item.isSameItemName(itemName)){
                item.addPromotion(promotion);
            }
        }
    }

    public List<Item> getItems(){
        return items;
    }

    public static boolean hasEnoughQuantityToBuy(String itemName, long quantityToBuy){
        Item item = getItemByName(itemName);
        return item.hasEnoughQuantityToBuy(quantityToBuy);
    }

    public static boolean hasPromotion(String itemName){
        getItemByName(itemName).validatePromotionDate();
        return getItemByName(itemName).hasPromotion();
    }

    public static boolean canBringMoreFreeItem(String itemName, long quantityToBuy){
        return getItemByName(itemName).canBringMoreFreeItem(quantityToBuy);
    }

    public static boolean hasEnoughPromotionQuantity(String itemName, long quantityToBuy){
        return getItemByName(itemName).hasEnoughPromotionQuantity(quantityToBuy);
    }

    public static long getInsufficientQuantity(String itemName, long quantityToBuy){
        return getItemByName(itemName).getInsufficientQuantity(quantityToBuy);
    }

    public static long getPriceByItemName(String itemName){
        return getItemByName(itemName).getPrice();
    }

    public static void decreaseItemQuantity(String itemName, long quantity){
        getItemByName(itemName).decreaseQuantity(quantity);
    }

    public static long getFreeQuantity(String itemName, long quantity){
        return getItemByName(itemName).getFreeQuantity(quantity);
    }

}
