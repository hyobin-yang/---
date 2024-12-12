package store.view;

import java.text.DecimalFormat;

public class OutputView {

    private static final String QUANTITY_UNIT = "개";

    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public void printWelcomeMessage(){
        System.out.println("안녕하세요. W편의점입니다.\n" +
                "현재 보유하고 있는 상품입니다.\n");
    }

    public void printCurrentItem(String itemName, long price, long quantity, String promotionName){
        String quantityToPrint = quantity + QUANTITY_UNIT;
        if (quantity == 0){
            quantityToPrint = "재고 없음";
        }
        if (promotionName.equals("null")){
            promotionName = "";
        }
        System.out.printf("- %s %s원 %s %s", itemName, decimalFormat.format(price), quantityToPrint, promotionName);
        System.out.println();
    }

    public void printReceiptTitle(){
        System.out.println("==============W 편의점================\n" +
                "상품명\t\t수량\t금액");
    }

    public void printPurchasedItem(String name, long quantity, long price){
        System.out.printf("%s\t\t%d \t%s", name, quantity, decimalFormat.format(price));
        System.out.println();
    }

    public void printFreeItemsTitle(){
        System.out.println("=============증\t정===============");
    }

    public void printFreeItem(String name, long quantity){
        System.out.printf("%s\t\t%d", name, quantity);
        System.out.println();
        System.out.println("====================================");
    }

    public void printTotalPrices(long totalItemPrice,
                                 long promotionDiscountPrice,
                                 long membershipDiscountPrice,
                                 long finalPayment,
                                 long totalQuantity){

        System.out.printf("총구매액\t\t%d\t%s\n" +
                "행사할인\t\t\t-%s\n" +
                "멤버십할인\t\t\t-%s\n" +
                "내실돈\t\t\t %s", totalQuantity, decimalFormat.format(totalItemPrice),
                decimalFormat.format(promotionDiscountPrice), decimalFormat.format(membershipDiscountPrice),
                decimalFormat.format(finalPayment));

    }

}
