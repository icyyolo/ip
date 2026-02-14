package zhongli.command;

import zhongli.gui.Dialogue;
import zhongli.product.ProductList;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;

/**
 * Represents a list product command
 *
 */
public class ListProductCommand extends Command {
    private static final String helpDescription =
            "Lists all products stored so far";

    private final ProductList productList;

    public ListProductCommand(ProductList productList) {
        this.productList = productList;
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, TaskStorage storage) {
        String productsString = this.productList.listProduct();
        dialogue.displayMessage(productsString);
    }

    public static String getHelpDescription() {
        return helpDescription;
    }
}
