package zhongli.alias;

import zhongli.command.CommandType;
import zhongli.parser.Parser;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents an alias class
 * which is an alternative name for a command
 *
 * It is optional for a command to have an alias
 *
 */
public class Alias {

    private final String alias;
    private final String originalCommand;

    /**
     * Represents an alias class which gets the alternative name of a command
     *
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
     * Returns the text to be inserted into the text file
     * alias + "|" + original command
     *
     */
    public String convertToText() {
        return this.getAlias() + "|" + this.getOriginalCommand();
    }

    /**
     * Check that alias has no space in the string
     *
     */
    public static void checkStringHasNoSpace(String alias) throws ZhongliException {
        if (alias.contains(" ")) {
            throw new ZhongliException("Alias should not have any spaces");
        }
    }

    /**
     * Checks the command if it exists in CommandType enum
     * Throws an error if command is not found
     *
     */
    public static void checkIsValidCommand(String command) throws ZhongliException {
        if (!CommandType.isValidCommand(command)) {
            throw new ZhongliException(command + " is not a valid keyword");
        }
    }

    /**
     * Transform a line in the text file to its corresponding alias
     * Throws an error when the regex is missing or when the original command is not valid
     *
     */
    public static Alias parseAliasFromTextFile(String line) throws ZhongliException {
        String[] inputs = Parser.splitStringIntoTwo(line, "|", "| is missing from the line");
        String alias = inputs[0];
        String originalCommand = inputs[1];

        checkIsValidCommand(originalCommand);

        return new Alias(alias, originalCommand);
    }

    /**
     * Transform a command from the user to its corresponding alias
     * Throws an error when the orignal command is not valid
     *
     * @param line
     * @return
     * @throws ZhongliException
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
