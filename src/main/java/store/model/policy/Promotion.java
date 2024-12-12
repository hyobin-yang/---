package store.model.policy;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int quantityToBuy;
    private final int quantityToGet;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int quantityToBuy, int quantityToGet, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.quantityToBuy = quantityToBuy;
        this.quantityToGet = quantityToGet;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isSamePromotionName(String promotionName){
        return name.equals(promotionName);
    }

    public String getName(){
        return name;
    }

    public int getTotalPromotionApplicableCount(){
        return quantityToBuy + quantityToGet;
    }

    public int getQuantityToBuy(){
        return quantityToBuy;
    }

    public int getQuantityToGet(){
        return quantityToGet;
    }

    public boolean isOngoingPromotion(){
        LocalDate today = DateTimes.now().toLocalDate();
        return (today.isAfter(startDate) && today.isBefore(endDate));
    }

}
