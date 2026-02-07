package zhongli.command;

import zhongli.gui.Dialogue;
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

    public String getKeywordFromCommand() throws ZhongliException {
        String[] keywordArr = Parser.splitStringIntoTwo(command, "find ",
                "Find phrase should not be empty");

        String keyword = keywordArr[1];

        if (keyword.isEmpty()) {
            throw new ZhongliException("Find phrase should not be empty");
        }

        return keyword;
    }

    /**
     * Find and display tasks that have the matching keyword from the task list
     *
     */
    public void executeCommand(TaskList taskList, Dialogue dialogue) {
        try {
            String keyword = getKeywordFromCommand();
            assert keyword != null : "keyword is null";

            TaskList matchedTask = taskList.getMatchingTask(keyword);
            assert matchedTask != null : "matchedTask is null";

            if (matchedTask.getSize() == 0) {
                dialogue.displayMessage("There is no task that match your keyword: " + keyword);
            } else {
                dialogue.addTaskList(matchedTask);
            }

        } catch (ZhongliException e) {
            dialogue.displayError(e.getMessage());
        }
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, Storage storage) {
        assert taskList != null : "taskList is null";
        assert dialogue != null : "gui is null";
        executeCommand(taskList, dialogue);
    }
}
