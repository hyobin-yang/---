package store.service;

import store.dto.ItemDTO;
import store.model.item.Item;
import store.model.policy.Promotion;
import store.repository.CurrentItemRepository;
import store.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemRegisterService {

    private static final String DELIMITER = ",";

    private final CurrentItemRepository currentItemRepository;
    private final PromotionRepository promotionRepository;

    public ItemRegisterService(CurrentItemRepository currentItemRepository, PromotionRepository promotionRepository){
        this.currentItemRepository = currentItemRepository;
        this.promotionRepository = promotionRepository;
    }

    public void initializePromotionData(List<String> rawPromotions){
        for (String rawPromotion : rawPromotions) {
            List<String> promotion = List.of(rawPromotion.split(DELIMITER));
            promotionRepository.addPromotion(new Promotion(promotion.get(0), Integer.parseInt(promotion.get(1)),
                    Integer.parseInt(promotion.get(2)), LocalDate.parse(promotion.get(3)),
                    LocalDate.parse(promotion.get(4))));
        }
    }

    public void initializedProductsData(List<String> rawProducts){
        for (String rawProduct : rawProducts){
            List<String> product = List.of(rawProduct.split(DELIMITER));
            if (currentItemRepository.hasItem(product.get(0))){
                updateItemQuantity(product);
                continue;
            }
            addNewItem(product);
        }
    }

    private void updateItemQuantity(List<String> product){
        if (product.get(3).equals("null")){
            currentItemRepository.increaseGeneralQuantity(product.get(0), Long.parseLong(product.get(2)));
            return;
        }
        currentItemRepository.increasePromotionQuantity(product.get(0), Long.parseLong(product.get(2)));
        currentItemRepository.addPromotion(product.get(0), promotionRepository.getPromotion(product.get(3)));
    }

    private void addNewItem(List<String> product){
        Item newItem = new Item(product.get(0), Long.parseLong(product.get(1)));
        if (product.get(3).equals("null")){
            newItem.increaseGeneralQuantity(Long.parseLong(product.get(2)));
            currentItemRepository.addItem(newItem);
            return;
        }
        newItem.increasePromotionQuantity(Long.parseLong(product.get(2)));
        newItem.addPromotion(promotionRepository.getPromotion(product.get(3)));
        currentItemRepository.addItem(newItem);
    }

    public List<ItemDTO> getCurrentItems(){
        List<Item> items = currentItemRepository.getItems();
        List<ItemDTO> DTOS = new ArrayList<>();
        for (Item item : items) {
            if (item.hasPromotion()){
                DTOS.add(new ItemDTO(item.getName(), item.getPrice(), item.getPromotionQuantity(), item.getPromotionName()));
                DTOS.add(new ItemDTO(item.getName(), item.getPrice(), item.getGeneralQuantity(), "null"));
                continue;
            }
            DTOS.add(new ItemDTO(item.getName(), item.getPrice(), item.getGeneralQuantity(), "null"));
        }
        return DTOS;
    }
}
