package Zhongli.Command;

import Zhongli.Parser.Parser;
import Zhongli.Storage.Storage;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;
import Zhongli.ZhongliException.ZhongliException;

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
                    "Find item should not be empty");

            keyword = keywordArr[1];
            
            if (keyword.isEmpty()) {
                throw new ZhongliException("Find phrase should not be empty");
            }
        } catch (ZhongliException e) {
            ui.displayExceptionMessage(e.getMessage());
            return;
        }

        System.out.println(keyword);
    }

}
