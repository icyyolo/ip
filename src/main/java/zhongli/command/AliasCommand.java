package zhongli.command;

import zhongli.alias.Alias;
import zhongli.alias.AliasList;
import zhongli.gui.Dialogue;
import zhongli.parser.Parser;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents an alias command
 *
 */
public class AliasCommand extends Command {
    private static final String helpDescription =
            "Create shorter forms for command";
    private final String command;
    private final AliasList aliasList;

    /**
     * Represents a command to add aliases
     *
     */
    public AliasCommand(String command, AliasList aliasList) {
        super();
        this.command = command;
        this.aliasList = aliasList;
    }

    /**
     * Try to format the string into an alias
     *
     */
    public void executeCommand(Dialogue dialogue) {
        try {
            String[] aliasInformation = Parser.splitStringIntoTwo(command, "alias ",
                    "Alias should not empty");

            String aliasString = aliasInformation[1];
            if (aliasString.isEmpty()) {
                throw new ZhongliException("Alias should not be empty");
            }

            Alias alias = Alias.parseAliasFromCommand(aliasString);
            aliasList.addAlias(alias);

            dialogue.displayMessage("Alias has been successfully added");
        } catch (ZhongliException e) {
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
