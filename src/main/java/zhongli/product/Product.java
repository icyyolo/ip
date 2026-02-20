package zhongli.product;

import zhongli.parser.Parser;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a product with a name and quantity. Provides functionality to parse products
 * from text file format and convert products back to text format for persistence.
 *
 */
public class Product {
    private static final String textFileItemSplitter = "/item/";

    private final String name;
    private int quantity;

    /**
     * Constructs a Product with the specified name and an initial quantity of zero.
     *
     * @param name The name of the product.
     */
    public Product(String name) {
        this.name = name;
        this.quantity = 0;
    }

    /**
     * Constructs a Product with the specified name and quantity.
     *
     * @param name The name of the product.
     * @param quantity The quantity of the product in stock.
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
     * Parses a product from a text file line in the format "name/item/quantity".
     * The product name is stripped of leading and trailing whitespace.
     *
     * @param line The text file line to parse.
     * @return A Product object created from the parsed line.
     * @throws ZhongliException If the name is empty, the quantity cannot be parsed as an integer,
     *                          or the line does not contain the "/item/" delimiter.
     */
    public static Product parseProductFromTextFile(String line) throws ZhongliException {
        String[] inputs = Parser.splitStringIntoTwo(line, textFileItemSplitter,
                " " + textFileItemSplitter + " missing from the line");
        String name = inputs[0].strip();
        if (name.isEmpty()) {
            throw new ZhongliException("Name cannot be empty");
        }
        int quantity;
        try {
            quantity = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
            throw new ZhongliException("Item cannot be converted to a number");
        }

        return new Product(name, quantity);
    }

    /**
     * Converts the product to its text file representation in the format "name/item/quantity".
     *
     * @return The product as a formatted text string suitable for file storage.
     */
    public String convertToText() {
        return this.name + textFileItemSplitter + this.quantity + "\n";
    }
}
