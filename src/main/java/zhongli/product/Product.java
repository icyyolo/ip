package zhongli.product;

/**
 * Represents a product class containing id name and quantity
 *
 */

public class Product {
    private final String name;
    private int quantity;

    /**
     * Initialize a product with name
     *
     */
    public Product(String name) {
        this.name = name;
        this.quantity = 0;
    }

    /**
     * Initialize a product with name and quantity
     *
     */
    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return this.name + " (Quantity: " + this.quantity + ").";
    }

}
