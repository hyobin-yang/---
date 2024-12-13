package store.dto;

import store.model.item.PurchasedItem;

import java.util.List;
import java.util.Map;

public record ReceiptDTO(List<PurchasedItem> purchasedItems, Map<String, Long> freeItems,
                                long totalItemPrice, long promotionDiscountPrice,
                                long membershipDiscountPrice, long finalPayment) {}
