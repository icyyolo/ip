package zhongli.command;

import zhongli.gui.Gui;
import zhongli.parser.Parser;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a find all the task that match a phrase command
 * If there is no task matching the phrase,
 * it will display a message that no task match the phrase.
 *
 */
public class FindCommand extends Command {
    private final String command;

    /**
     * Represents a Find Task command
     *
     * @param command - command entered by user
     */
    public FindCommand(String command) {
        super();
        this.command = command;
    }

    public String executeCommand(TaskList taskList, Storage storage) {
        String[] keywordArr;
        String keyword;

        try {
            keywordArr = Parser.splitStringIntoTwo(command, "find ",
                    "Find phrase should not be empty");

            keyword = keywordArr[1];

            if (keyword.isEmpty()) {
                throw new ZhongliException("Find phrase should not be empty");
            }
        } catch (ZhongliException e) {
            return e.getMessage();
        }

        String matchedTask = taskList.getMatchingTask(keyword);
        return Ui.getFindMessage(matchedTask, keyword);
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        String[] keywordArr;
        String keyword;

        try {
            keywordArr = Parser.splitStringIntoTwo(command, "find ",
                    "Find phrase should not be empty");

            keyword = keywordArr[1];

            if (keyword.isEmpty()) {
                throw new ZhongliException("Find phrase should not be empty");
            }
        } catch (ZhongliException e) {
            ui.displayExceptionMessage(e.getMessage());
            return;
        }

        String matchedTask = taskList.getMatchingTask(keyword);

        ui.displayFindMessage(matchedTask, keyword);
    }

    @Override
    public String runGui(TaskList taskList, Gui gui, Storage storage) {
        return executeCommand(taskList, storage);
    }
}
