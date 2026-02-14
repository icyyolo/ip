package zhongli.command;

import java.io.IOException;

import zhongli.gui.Dialogue;
import zhongli.parser.Parser;
import zhongli.product.Product;
import zhongli.product.ProductList;
import zhongli.storage.ProductStorage;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents an add product command
 */
public class AddProductCommand extends Command {
    private static final String helpDescription =
            "Add product so zhongli can help u track it.";

    private final String command;
    private final ProductList productList;
    private final ProductStorage productStorage;

    /**
     * Represents a command to add product
     *
     */
    public AddProductCommand(String command, ProductList productList, ProductStorage productStorage) {
        super();
        this.command = command;
        this.productList = productList;
        this.productStorage = productStorage;
    }

    /**
     * Execute the command given the parameters
     *
     */
    public void executeCommand(Dialogue dialogue) {
        String name;
        try {
            String[] addArr = Parser.splitStringIntoTwo(
                    this.command, "addproduct", "Missing add command");
            name = addArr[1].strip();
        } catch (ZhongliException e) {
            dialogue.displayError(e.getMessage());
            return;
        }

        if (name.isEmpty()) {
            dialogue.displayError("Name cannot be empty");
            return;
        }

        int quantity;
        try {
            String[] quantityArr = Parser.splitStringIntoTwo(
                    this.command, "/item", "");
            quantity = Integer.parseInt(quantityArr[1].strip());
            name = quantityArr[0];
        } catch (ZhongliException e) {
            quantity = 0;
        } catch (NumberFormatException e) {
            dialogue.displayError("Number after /item is invalid");
            return;
        }

        Product newProduct = new Product(name, quantity);
        productList.addProduct(newProduct);
        try {
            productStorage.writeProductListToFile(productList);
        } catch (IOException e) {
            dialogue.displayError(e.getMessage());
            return;
        }

        dialogue.displayMessage("Successfully added product");
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, TaskStorage storage) {
        executeCommand(dialogue);
    }

    public static String getHelpDescription() {
        return helpDescription;
    }
}
