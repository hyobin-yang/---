package store.repository;

import store.model.policy.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {

    private final List<Promotion> promotions = new ArrayList<>();

    public void addPromotion(Promotion promotion){
        promotions.add(promotion);
    }

    public Promotion getPromotion(String promotionName){
        for (Promotion promotion : promotions) {
            if (promotion.isSamePromotionName(promotionName)){
                return promotion;
            }
        }
        throw new IllegalArgumentException("[SYSTEM] 존재하지 않는 프로모션입니다.");
    }
}
