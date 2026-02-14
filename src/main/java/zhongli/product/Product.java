package zhongli.product;

import zhongli.parser.Parser;
import zhongli.zhongliexception.ZhongliException;

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

    /**
     * Transform a line in the text file to its corresponding product
     *
     */
    public static Product parseProductFromTextFile(String line) throws ZhongliException {
        String[] inputs = Parser.splitStringIntoTwo(line, "/item/", "/item missing from the line");
        String name = inputs[0].strip();
        if (name.isEmpty()) {
            throw new ZhongliException("Name cannot be empty");
        }
        int quantity;
        try {
            quantity = Integer.parseInt(inputs[0]);
        } catch (NumberFormatException e) {
            throw new ZhongliException("Item cannot be converted to a number");
        }

        return new Product(name, quantity);
    }

    public String convertToText() {
        return this.name + "/item/" + this.quantity + "\n";
    }
}
