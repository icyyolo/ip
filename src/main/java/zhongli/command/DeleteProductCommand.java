package zhongli.command;

import java.io.IOException;

import zhongli.gui.Dialogue;
import zhongli.product.ProductList;
import zhongli.storage.ProductStorage;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a Delete Product in product list command
 *
 */
public class DeleteProductCommand extends Command {
    private static final String helpDescription =
            "Deletes a task from the task list";

    private final String command;
    private final ProductList productList;
    private final ProductStorage productStorage;

    /**
     * Represents a Delete Task command
     *
     * @param command - command entered by user
     */
    public DeleteProductCommand(String command, ProductList productList, ProductStorage productStorage) {
        super();
        this.command = command;
        this.productList = productList;
        this.productStorage = productStorage;
    }

    /**
     * Gets the index of the deleted task
     *
     */
    public int parseIndexForDeletedTask() throws NumberFormatException {
        String number = this.command.split(" ")[1];
        int index = Integer.parseInt(number) - 1;
        assert index >= 0 : "Index should not be less than 0";
        return index;
    }

    /**
     * Deletes the product from the product list
     *
     */
    public void executeCommand(Dialogue dialogue) {
        try {
            int index = parseIndexForDeletedTask();
            productList.deleteProduct(index);

            productStorage.writeProductListToFile(productList);

            dialogue.displayMessage("Successfully deleted Product");
        } catch (IndexOutOfBoundsException e) {
            dialogue.displayError("Please input a number after delete");
        } catch (NumberFormatException e) {
            dialogue.displayError("Please input a valid number");
        } catch (ZhongliException | IOException e) {
            dialogue.displayError(e.getMessage());
        }
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, TaskStorage storage) {
        executeCommand(dialogue);
    }

    public static String getHelpDescription() {
        return helpDescription;
    }
}
