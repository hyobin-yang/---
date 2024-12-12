package store.model.item;

import store.model.policy.Promotion;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private final String name;
    private final long price;
    private long generalQuantity = 0;
    private long promotionQuantity = 0;
    private final List<Promotion> promotions = new ArrayList<>();

    public Item(String name, long price){
        this.name = name;
        this.price = price;
    }

    public void increaseGeneralQuantity(long quantity){
        generalQuantity += quantity;
    }

    public void increasePromotionQuantity(long quantity){
        promotionQuantity += quantity;
    }

    public void addPromotion(Promotion promotion){
        promotions.add(promotion);
    }

    public void clearPromotion(){
        promotions.clear();
    }

    public void validatePromotionCount(){
        if (promotions.size() > 1){
            throw new IllegalArgumentException("[SYSTEM] 프로모션은 한 상품당 하나만 적용될 수 있습니다.");
        }
    }

    public boolean isSameItemName(String itemName){
        return name.equals(itemName);
    }

    public String getName(){
        return name;
    }

    public long getPrice(){
        return price;
    }

    public long getGeneralQuantity(){
        return generalQuantity;
    }

    public long getPromotionQuantity(){
        return promotionQuantity;
    }

    public boolean hasPromotion(){
        return promotions.size() == 1;
    }

    public String getPromotionName(){
        return promotions.get(0).getName();
    }

    public boolean hasEnoughQuantityToBuy(long quantity){
        return (generalQuantity + promotionQuantity) >= quantity;
    }

    public boolean canBringMoreFreeItem(long quantityToBuy){
        return ((quantityToBuy + 1) %
                (promotions.get(0).getQuantityToBuy() + promotions.get(0).getQuantityToGet())) == 0;
    }

    public void validatePromotionDate(){
        if (!promotions.isEmpty() && !promotions.get(0).isOngoingPromotion()){
            generalQuantity += promotionQuantity;
            promotionQuantity = 0;
            promotions.clear();
        }
    }

    public boolean hasEnoughPromotionQuantity(long quantityToBuy){
        return ( quantityToBuy / promotions.get(0).getTotalPromotionApplicableCount() ) *
                promotions.get(0).getTotalPromotionApplicableCount() <= promotionQuantity;
    }

    public long getInsufficientQuantity(long quantityToBuy){
        return ( quantityToBuy / promotions.get(0).getTotalPromotionApplicableCount() ) *
                promotions.get(0).getTotalPromotionApplicableCount() - promotionQuantity;
    }

    public void decreaseQuantity(long quantity){
        if (hasPromotion()){
            if (quantity > promotionQuantity){
                generalQuantity -= (quantity - promotionQuantity);
                promotionQuantity = 0;
            }
            promotionQuantity -= quantity;
            return;
        }
        generalQuantity -= quantity;
    }

    public long getFreeQuantity(long quantity){
        if (promotions.isEmpty()){
            return 0;
        }
        if (promotionQuantity >= quantity){
            return quantity / promotions.get(0).getTotalPromotionApplicableCount();
        }
        return promotionQuantity / promotions.get(0).getTotalPromotionApplicableCount();
    }

}
