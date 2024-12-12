package store.model.policy;

public enum Membership {

    POLICY(30, 8_000);

    private final int discountRate;
    private final long maximumDiscountPrice;

    Membership(int discountRate, long maximumDiscountPrice){
        this.discountRate = discountRate;
        this.maximumDiscountPrice = maximumDiscountPrice;
    }

    public static long applyMembership(long priceToApply){
        long discountedPrice = priceToApply * ( (100 - POLICY.discountRate) / 100);
        if (discountedPrice > POLICY.maximumDiscountPrice){
            return POLICY.maximumDiscountPrice;
        }
        return discountedPrice;
    }
}
