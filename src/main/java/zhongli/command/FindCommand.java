package zhongli.command;

import zhongli.parser.Parser;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

public class FindCommand extends Command{
    private final String command;

    public FindCommand(String command) {
        super();
        this.command = command;
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

}
