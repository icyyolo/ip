package zhongli.command;

import zhongli.gui.Gui;
import zhongli.parser.Parser;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
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

    public void executeCommand(TaskList taskList, Gui gui, Storage storage) {
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
            gui.displayError(e.getMessage());
            return;
        }

        TaskList matchedTask = taskList.getMatchingTask(keyword);
        assert matchedTask != null : "matchedTask is null";
        if (matchedTask.getSize() == 0) {
            gui.displayMessage("There is no task that match your keyword: " + keyword);
            return;
        }
        gui.addTaskList(matchedTask);
    }

    @Override
    public void runGui(TaskList taskList, Gui gui, Storage storage) {
        assert taskList != null : "taskList is null";
        assert storage != null : "storage is null";
        assert gui != null : "gui is null";
        executeCommand(taskList, gui, storage);
    }
}
