package za.ac.mycput.musicalnote_backend.Factory;

import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.util.Helper;

public class ProductFactory {
    public static Product buildproduct(Long productId, String name, String description, float price, String category, int stock){
        if(Helper.isNullOrEmpty(productId) ||
             Helper.isNullOrEmpty(name)||
                Helper.isNullOrEmpty(description) ||
                Helper.isNullOrEmpty(category) ||
                price < 0.0f ||
                stock < 0
        ){
            return null;
        }

        return new Product.Builder()
                .setProductId(productId)
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setCategory(category)
                .setPrice(price)
                .setStock(stock)
                .build();


    }
}
