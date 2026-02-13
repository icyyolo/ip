package zhongli.product;

import java.util.ArrayList;

/**
 * Represents an ArrayList of products.
 *
 */
public class ProductList {
    private ArrayList<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }

    public ProductList(ArrayList<Product> products) {
        this.products = products;
    }


}
