package store.io;

import java.util.List;

public class ConvenienceStoreDataProvider {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";

    public static List<String> productsDataProvider(){
        return FileReader.read(PRODUCTS_FILE_PATH);
    }

    public static List<String> promotionsDataProvider(){
        return FileReader.read(PROMOTIONS_FILE_PATH);
    }

}
