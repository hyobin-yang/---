package store.dto;

public class OrderedItemDTO {

    private final String name;
    private long quantity;

    public OrderedItemDTO(String name, long quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String name(){
        return name;
    }

    public long quantity(){
        return quantity;
    }

    public void increaseQuantity(long quantityToIncrease){
        quantity += quantityToIncrease;
    }

    public void decreaseQuantity(long quantityToDecrease){
        quantity -= quantityToDecrease;
    }
}
