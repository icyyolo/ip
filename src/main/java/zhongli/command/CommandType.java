package zhongli.command;


public enum CommandType {
    LIST("list"),
    TODO("todo"),
    EVENT("event"),
    DEADLINE("deadline"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    BYE("bye"),
    FIND("find"),
    UNKNOWN(""); // For invalid commands

    private final String keyword;

    CommandType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
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
