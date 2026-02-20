package zhongli.alias;

import zhongli.command.CommandType;
import zhongli.parser.Parser;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents an alternative name for a command. Each alias maps a user-defined shorthand
 * to an original command keyword. Provides functionality to validate, parse, and convert
 * aliases to and from text file format.
 *
 */
public class Alias {

    private final String alias;
    private final String originalCommand;

    /**
     * Constructs an Alias that maps the specified alias name to an original command.
     *
     * @param alias The shorthand alias name.
     * @param originalCommand The original command keyword this alias represents.
     */
    public Alias(String alias, String originalCommand) {
        this.alias = alias;
        this.originalCommand = originalCommand;
    }

    public Boolean checkAlias(String aliasString) {
        return this.alias.equals(aliasString);
    }

    public String getAlias() {
        return this.alias;
    }

    public String getOriginalCommand() {
        return this.originalCommand;
    }

    /**
     * Replaces the first occurrence of this alias with its original command in the provided
     * command string. Used to convert user input containing the alias into the original command.
     *
     * @param currCommand The command string containing the alias to replace.
     * @return The command string with the alias replaced by the original command.
     */
    public String refactorCommandToOriginal(String currCommand) {
        return currCommand.replaceFirst(this.alias, this.originalCommand);
    }

    /**
     * Converts the alias to its text file representation in the format "alias/alias/originalCommand".
     *
     * @return The alias formatted as a text file line suitable for persistent storage.
     */
    public String convertToText() {
        return this.getAlias() + "/alias/" + this.getOriginalCommand() + "\n";
    }

    /**
     * Validates that the provided alias string contains no whitespace characters.
     *
     * @param alias The alias string to validate.
     * @throws ZhongliException If the alias contains any spaces.
     */
    public static void checkStringHasNoSpace(String alias) throws ZhongliException {
        if (alias.contains(" ")) {
            throw new ZhongliException("Alias should not have any spaces");
        }
    }

    /**
     * Validates that the provided command string is a recognized command keyword in the system.
     *
     * @param command The command string to validate.
     * @throws ZhongliException If the command is not a valid recognized keyword.
     */
    public static void checkIsValidCommand(String command) throws ZhongliException {
        if (!CommandType.isValidCommand(command)) {
            throw new ZhongliException(command + " is not a valid keyword");
        }
    }

    /**
     * Parses an alias from a text file line in the format "alias/alias/originalCommand".
     * Validates that the original command is a recognised keyword before creating the alias.
     *
     * @param line A line from the text file containing the alias definition.
     * @return An Alias object parsed from the file line.
     * @throws ZhongliException If the line format is invalid, the delimiter is missing,
     *                          or the original command is not a valid keyword.
     */
    public static Alias parseAliasFromTextFile(String line) throws ZhongliException {
        String[] inputs = Parser.splitStringIntoTwo(line, "/alias/", "/alias/ is missing from the line");
        String alias = inputs[0];
        String originalCommand = inputs[1].replace("/alias/", "");
        checkIsValidCommand(originalCommand);
        System.out.println();
        return new Alias(alias, originalCommand);
    }

    /**
     * Parses an alias from user input in the format "originalCommand aliasName".
     * Validates that the alias contains no spaces and the original command is a valid keyword.
     *
     * @param line A command line from the user in the format "originalCommand aliasName".
     * @return An Alias object created from the user input.
     * @throws ZhongliException If the format is invalid, the alias contains spaces,
     *                          or the original command is not a valid keyword.
     */
    public static Alias parseAliasFromCommand(String line) throws ZhongliException {
        String[] inputs = Parser.splitStringIntoTwo(line, " ",
                "There should be a space between the command and the alias");
        String originalCommand = inputs[0];
        String alias = inputs[1];

        checkStringHasNoSpace(alias);

        checkIsValidCommand(originalCommand);

        return new Alias(alias, originalCommand);
    }

}
