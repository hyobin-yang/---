package store.config;

import store.controller.ConvenienceStoreController;
import store.model.item.Item;
import store.repository.CurrentItemRepository;
import store.repository.PromotionRepository;
import store.service.ItemPurchasingService;
import store.service.ItemRegisterService;
import store.service.OrderedItemRegisterService;
import store.service.ReceiptService;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {

    private InputView inputView(){
        return new InputView();
    }

    private OutputView outputView(){
        return new OutputView();
    }

    private CurrentItemRepository currentItemRepository(){
        return new CurrentItemRepository();
    }

    private PromotionRepository promotionRepository(){
        return new PromotionRepository();
    }

    private ItemRegisterService itemRegisterService(){
        return new ItemRegisterService(currentItemRepository(), promotionRepository());
    }

    private OrderedItemRegisterService orderedItemRegisterService(){
        return new OrderedItemRegisterService();
    }

    private ItemPurchasingService itemPurchasingService(){
        return new ItemPurchasingService();
    }

    private ReceiptService receiptService(){
        return new ReceiptService();
    }

    public ConvenienceStoreController controller() {
        return new ConvenienceStoreController(inputView(), outputView(),
                itemRegisterService(), orderedItemRegisterService(),
                itemPurchasingService(), receiptService());
    }
}
