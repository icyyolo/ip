package zhongli.gui;

import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a Mimic of User Interface class
 * This returns a String to output to the GUI
 *
 */
public class Gui {

    public static String getWelcomeMessage() {
        StringBuilder welcomeMessage = new StringBuilder();
        welcomeMessage.append("Hello! I'm Zhongli\n")
                .append("What can I do for you?\n");
        return welcomeMessage.toString();
    }

    public static String getTasksArrayString(TaskList tasks) {
        return tasks.toString();
    }

}
