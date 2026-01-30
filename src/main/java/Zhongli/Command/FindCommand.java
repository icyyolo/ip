package Zhongli.Command;

import Zhongli.Storage.Storage;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;

public class FindCommand extends Command{
    private final String command;

    public FindCommand(String command) {
        super();
        this.command = command;
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        
    }

}
