package zhongli.command;

/**
 * Enumeration of all valid command types recognized by the application.
 * Each command type has an associated keyword and help message describing its usage.
 * The UNKNOWN type represents invalid or unrecognized command input.
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
    ALIAS("alias", AliasCommand.getHelpDescription()),
    ADDPRODUCT("addproduct", AddProductCommand.getHelpDescription()),
    LISTPRODUCT("listproduct", ListProductCommand.getHelpDescription()),
    DELETEPRODUCT("deleteproduct", DeleteProductCommand.getHelpDescription()),
    UNKNOWN(""); // For invalid commands

    private final String keyword;
    private final String helpMessage;

    /**
     * Constructs a CommandType with the specified keyword and help message.
     *
     * @param keyword The command keyword string.
     * @param helpMessage The help description for this command.
     */
    CommandType(String keyword, String helpMessage) {
        this.keyword = keyword;
        this.helpMessage = helpMessage;
    }

    /**
     * Constructs a CommandType with only a keyword, used for the UNKNOWN command type.
     * The help message is initialized to an empty string.
     *
     * @param keyword The command keyword string.
     */
    CommandType(String keyword) {
        this.keyword = keyword;
        this.helpMessage = "";
    }

    public String getKeyword() {
        return keyword;
    }

    /**
     * Returns a formatted string containing help messages for all valid commands.
     * Each command's keyword and help message are displayed on a separate line.
     * The UNKNOWN command type is excluded from the output.
     *
     * @return A formatted string containing help information for all recognised commands.
     */
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
     * Validates whether the provided keyword matches a valid command in the enumeration.
     * Empty strings are considered invalid.
     *
     * @param keyword The keyword string to validate.
     * @return True if the keyword corresponds to a valid command, false otherwise.
     */
    public static boolean isValidCommand(String keyword) {

        if (keyword.isEmpty()) {
            return false;
        }

        for (CommandType type : CommandType.values()) {
            if (type.keyword.equals(keyword)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the CommandType corresponding to the provided keyword.
     * If the keyword does not match any command in the enumeration, returns UNKNOWN.
     *
     * @param keyword The command keyword string to look up.
     * @return The corresponding CommandType, or UNKNOWN if the keyword is not recognized.
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
