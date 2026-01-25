package Zhongli.Command;

import Zhongli.Storage.Storage;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;

public class ByeCommand extends Command{

    public ByeCommand () {
        super();
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        super.setIsExit(true);
    }
}
