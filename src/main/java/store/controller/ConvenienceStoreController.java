package store.controller;

import store.dto.ItemDTO;
import store.dto.OrderedItemDTO;
import store.dto.ReceiptDTO;
import store.handler.RetryHandler;
import store.io.ConvenienceStoreDataProvider;
import store.model.item.PurchasedItem;
import store.model.policy.Answer;
import store.service.ItemPurchasingService;
import store.service.OrderedItemRegisterService;
import store.service.ItemRegisterService;
import store.service.ReceiptService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.Map;

public class ConvenienceStoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ItemRegisterService itemRegisterService;
    private final OrderedItemRegisterService itemOrderService;
    private final ItemPurchasingService itemPurchasingService;
    private final ReceiptService receiptService;

    public ConvenienceStoreController(InputView inputView, OutputView outputView,
                                      ItemRegisterService itemRegisterService, OrderedItemRegisterService itemOrderService,
                                      ItemPurchasingService itemPurchasingService, ReceiptService receiptService){
        this.inputView = inputView;
        this.outputView = outputView;
        this.itemRegisterService = itemRegisterService;
        this.itemOrderService = itemOrderService;
        this.itemPurchasingService = itemPurchasingService;
        this.receiptService = receiptService;
    }

    public void initializeConvenienceStoreData(){
        initializePromotionData();
        initializeProductsData();
    }

    private void initializePromotionData(){
        itemRegisterService.initializePromotionData(ConvenienceStoreDataProvider.promotionsDataProvider());
    }

    private void initializeProductsData(){
        itemRegisterService.initializedProductsData(ConvenienceStoreDataProvider.productsDataProvider());
    }

    public void run(){
        RetryHandler.retryWithoutReturn(this::startPurchasing);
    }

    private void startPurchasing(){
        showStartMessages();
        List<OrderedItemDTO> orderedItems = RetryHandler.retryWithReturn(() ->
                itemOrderService.registerOrderedItemDTO(inputView.inputOrder()));
        handlePromotionPolicy(orderedItems);
        boolean hasMembership = willApplyMembership();
        printReceipt(orderedItems, hasMembership);
        if (inputView.willApplyMembership().equals(Answer.POSITIVE)){
            run();
        }
    }

    private void showStartMessages(){
        outputView.printWelcomeMessage();
        List<ItemDTO> itemDTOS = itemRegisterService.getCurrentItems();
        for (ItemDTO DTO : itemDTOS) {
            outputView.printCurrentItem(DTO.name(), DTO.price(), DTO.quantity(), DTO.promotionName());
        }
    }

    private void handlePromotionPolicy(List<OrderedItemDTO> orderedItems){
        for (OrderedItemDTO orderedItem : orderedItems) {
            if (itemPurchasingService.canBringMoreFreeItem(orderedItem)){
                RetryHandler.retryWithoutReturn(() -> bringMoreItemOrNot(orderedItem));
            }
            if (!itemPurchasingService.hasEnoughPromotionQuantity(orderedItem)){
                long insufficientQuantity = itemPurchasingService.getInsufficientQuantity(orderedItem);
                RetryHandler.retryWithoutReturn(() -> willPurchaseWithoutPromotionDiscount(orderedItem, insufficientQuantity));
            }
        }
    }

    private void bringMoreItemOrNot(OrderedItemDTO orderedItem){
        if (inputView.willBringMoreItem(orderedItem.name(), 1).equals(Answer.POSITIVE)){
            itemPurchasingService.bringMoreFreeItem(orderedItem);
        }
    }

    private void willPurchaseWithoutPromotionDiscount(OrderedItemDTO orderedItem, long insufficientQuantity){
        if (!inputView.willPurchaseWithoutPromotionDiscount(orderedItem.name(), insufficientQuantity).equals(Answer.POSITIVE)){
            orderedItem.decreaseQuantity(insufficientQuantity);
        }
    }

    private boolean willApplyMembership(){
        return RetryHandler.retryWithReturn(inputView::willApplyMembership).equals(Answer.POSITIVE);
    }

    private void printReceipt(List<OrderedItemDTO> orderedItems, boolean hasMembership){
        ReceiptDTO receipt = receiptService.getReceipt(orderedItems, hasMembership);
        outputView.printReceiptTitle();
        printPurchasedItems(receipt.purchasedItems());
        outputView.printFreeItemsTitle();
        printFreeItems(receipt.freeItems());
        printTotalPrices(receipt, getTotalQuantity(receipt.purchasedItems()));
    }

    private void printPurchasedItems(List<PurchasedItem> items){
        for (PurchasedItem item : items) {
            outputView.printPurchasedItem(item.name(), item.quantity(), item.price());
        }
    }

    private void printFreeItems(Map<String, Long> freeItems){
        for (Map.Entry<String, Long> item :freeItems.entrySet()) {
            outputView.printFreeItem(item.getKey(), item.getValue());
        }
    }

    private long getTotalQuantity(List<PurchasedItem> items){
        long totalQuantity = 0;
        for (PurchasedItem item : items) {
            totalQuantity += item.quantity();
        }
        return totalQuantity;
    }

    private void printTotalPrices(ReceiptDTO receipt, long totalQuantity){
        outputView.printTotalPrices(receipt.totalItemPrice(), receipt.promotionDiscountPrice(),
                receipt.membershipDiscountPrice(), receipt.finalPayment(), totalQuantity);
    }

}
