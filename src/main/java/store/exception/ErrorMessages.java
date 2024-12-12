package store.exception;

public enum ErrorMessages {

    INVALID_ITEM_INPUT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    ITEM_NOT_EXIST("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCEED_ITEM_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_ANSWER_INPUT("올바르지 않은 대답 형식입니다. 다시 입력해 주세요."),
    GENERAL_INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요.");

    private final String message;

    ErrorMessages(String message){
        this.message = message;
    }

    public String getMessage(){
        return String.format("[ERROR] %s", message);
    }
}