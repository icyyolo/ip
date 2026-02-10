package zhongli.command;

/**
 * enum of all possible command entered by user
 *
 */
public enum CommandType {
    LIST("list", ListTaskCommand.getHelpDescription()),
    TODO("todo", AddTaskCommand.getHelpDescription("todo")),
    EVENT("event", AddTaskCommand.getHelpDescription("event")),
    DEADLINE("deadline", AddTaskCommand.getHelpDescription("deadline")),
    MARK("mark", MarkCommand.getHelpDescription()),
    UNMARK("unmark", UnmarkCommand.getHelpDescription()),
    DELETE("delete", DeleteCommand.getHelpDescription()),
    BYE("bye", ByeCommand.getHelpDescription()),
    FIND("find", FindCommand.getHelpDescription()),
    HELP("help", HelpCommand.getHelpDescription()),
    UNKNOWN(""); // For invalid commands

    private final String keyword;
    private final String helpMessage;

    CommandType(String keyword, String helpMessage) {
        this.keyword = keyword;
        this.helpMessage = helpMessage;
    }

    /**
     * This is the case for unknown, as there is no such thing as an unknown command
     *
     */
    CommandType(String keyword) {
        this.keyword = keyword;
        this.helpMessage = "";
    }

    public String getKeyword() {
        return keyword;
    }

    public static String getAllCommandsHelpMessage() {
        StringBuilder helpMessage = new StringBuilder();
        for (CommandType type : CommandType.values()) {

            // Skip unknown command
            if (type.keyword.equals("")) {
                continue;
            }

            helpMessage.append(type.keyword)
                    .append(": ")
                    .append(type.helpMessage)
                    .append("\n");
        }
        return helpMessage.toString();
    }

    /**
     * Get the type of command from the keyword
     * If the keyword is not in the enum, it is treated as a wrong keyword.
     * It will return UNKNOWN
     *
     */
    public static CommandType fromString(String keyword) {
        for (CommandType type : CommandType.values()) {
            if (type.keyword.equals(keyword)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
