package store.model.receipt;

import store.dto.ReceiptDTO;
import store.model.item.PurchasedItem;
import store.model.policy.Membership;

import java.util.List;
import java.util.Map;

public class Receipt {

    private List<PurchasedItem> purchasedItems;
    private Map<String, Long> freeItems;
    private long totalItemPrice = 0;
    private long promotionDiscountPrice = 0;
    private long membershipDiscountPrice = 0;
    private long finalPayment;

    public void setPurchasedItems(List<PurchasedItem> purchasedItems){
        this.purchasedItems = purchasedItems;
    }

    public void setFreeItems(Map<String, Long> freeItems) {
        this.freeItems = freeItems;
    }

    public void updateTotalPrice(boolean hasMembership){
        updateTotalItemPrice();
        updatePromotionDiscountPrice();
        if (hasMembership){
            updateMembershipDiscountPrice();
        }
        updateFinalPayment();
    }

    private void updateTotalItemPrice(){
        for (PurchasedItem item : purchasedItems){
            totalItemPrice += item.price() * item.quantity();
        }
    }

    private void updatePromotionDiscountPrice(){
        for ( Map.Entry<String, Long> freeItem : freeItems.entrySet()){
            for (PurchasedItem item : purchasedItems){
                if (item.name().equals(freeItem.getKey())){
                    promotionDiscountPrice += item.price() * freeItem.getValue();
                }
            }
        }
    }

    private void updateMembershipDiscountPrice(){
        membershipDiscountPrice += Membership.applyMembership(totalItemPrice - promotionDiscountPrice);
    }

    private void updateFinalPayment(){
        finalPayment = totalItemPrice - promotionDiscountPrice - membershipDiscountPrice;
    }

    public ReceiptDTO getReceipt(){
        return new ReceiptDTO(purchasedItems, freeItems, totalItemPrice, promotionDiscountPrice, membershipDiscountPrice, finalPayment);
    }

}
