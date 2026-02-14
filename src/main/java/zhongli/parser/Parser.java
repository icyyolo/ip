package zhongli.parser;

import java.time.DateTimeException;
import java.time.LocalDate;

import zhongli.alias.Alias;
import zhongli.alias.AliasList;
import zhongli.command.*;
import zhongli.product.ProductList;
import zhongli.storage.AliasStorage;
import zhongli.task.Deadline;
import zhongli.task.Event;
import zhongli.task.Task;
import zhongli.task.ToDo;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a parser class
 * It transforms a string to its respective objects.
 * If there is any error, it will return a ZhongliException
 *
 */
public class Parser {

    /**
     * Returns a LocalDate object given the String dateText.
     * Some special keywords include,
     * "now" -> returns the current Date.
     *
     * @param dateText - A string formatted in LocalDate format (YYYY-MM-DD) / special keywords
     * @return A LocalDate object formatted from the string given
     * @throws ZhongliException If dateText does not match the format of LocalDate object
     */
    public static LocalDate parseDate(String dateText) throws ZhongliException {
        // Special cases:
        if (dateText.equals("now")) {
            return LocalDate.now();
        }

        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeException e) {
            throw new ZhongliException(e.getMessage()
                    + "\nDate Should be in this format YYYY-MM-DD");
        }
    }

    /**
     * Splits the string into 2 based on first occurrence of the string regex.
     * Returns the array of 2 elements.
     *
     * @param input - String to split into 2.
     * @param regex - Phrase to split string by.
     * @param errorMsg - Message to display if regex is not found.
     * @return - Array of 2 elements which are before and after regex.
     * @throws ZhongliException - If regex is not found in the string.
     */
    public static String[] splitStringIntoTwo(String input, String regex, String errorMsg) throws ZhongliException {
        assert regex != null : "regex is null";
        String[] res = input.split(regex, 2);
        assert errorMsg != null : "errorMsg is null";
        if (res.length < 2) {
            throw new ZhongliException(errorMsg);
        }

        return res;
    }

    /**
     * Checks if the string is empty.
     *
     * @param input - String to be checked.
     * @param errorMsg - Message to display if input is empty.
     * @throws ZhongliException - If input is empty.
     */
    private static void checkStringIsEmpty(String input, String errorMsg) throws ZhongliException {
        assert errorMsg != null : "errorMsg is null";
        if (input.isEmpty()) {
            throw new ZhongliException(errorMsg);
        }
    }

    /**
     * Process the string read from the text file into a Task object.
     * Check if the task should be mark or not.
     * If there is no indication of the task being mark, it is an invalid string.
     * The text will be parsed to the correct parse function to transform it into a task.
     *
     * @param line - String read from the text file.
     * @return task - after parsing from the correct parse function.
     * @throws ZhongliException - If String is invalid after it parse.
     */
    public static Task parseTaskFromTextFile(String line) throws ZhongliException {
        String typeOfTask = line.split(" ")[0].toLowerCase();

        boolean isMark = line.contains("/mark");
        boolean isUnmark = line.contains("/unmark");

        if ((isMark && isUnmark) || (!isMark && !isUnmark)) {
            throw new ZhongliException("Confusion about /mark and /unmark in text file");
        }

        return switch (typeOfTask) {
        case ("todo") -> Parser.parseToDo(line, isMark);
        case ("deadline") -> Parser.parseDeadline(line, isMark);
        case ("event") -> Parser.parseEvent(line, isMark);
        default -> throw new ZhongliException("task type not found");
        };
    }

    /**
     * Process the string given by the user into a Task object.
     * Checks which type of task is the input.
     * If the first word does not identify which task it is, it will throw an exception.
     * The text will be parses into the correct parse function.
     *
     * @param line - String entered by user.
     * @return task - after parsing from the correct parse function.
     * @throws ZhongliException - If String does not identify the task, or error in the respective parse function.
     */
    public static Task parseTaskFromInput(String line) throws ZhongliException {
        String typeOfTask = line.split(" ")[0].toLowerCase();

        return switch (typeOfTask) {
        case ("todo") -> Parser.parseToDo(line);
        case ("deadline") -> Parser.parseDeadline(line);
        case ("event") -> Parser.parseEvent(line);
        default -> throw new ZhongliException("task type not found");
        };
    }

    /**
     * Formate the string to exclude "/mark" or "/unmark" at the end.
     *
     * @param input - String that you want to exclude /mark or /unmark
     * @param isDone - Boolean to check for mark or unmark
     * @return String without /mark or /unmark
     */
    public static String formatTextToExcludeIsMark(String input, boolean isDone) {
        return isDone
                ? input.split("/mark")[0]
                : input.split("/unmark")[0];
    }

    /**
     * Transform the string input from user input into a valid ToDo Object.
     * It will checks that the string contains a non-empty description.
     *
     * @throws ZhongliException if the description of todo is empty.
     */
    public static ToDo parseToDo(String input) throws ZhongliException {
        String[] toDoArr = splitStringIntoTwo(input, "todo", "Missing Description of ToDo");

        String description = toDoArr[1].trim();
        checkStringIsEmpty(description, "Description cannot be empty");

        return new ToDo(description);
    }

    /**
     * Transform the string input from text file into a valid ToDo Object.
     * It will checks that the string contains a non-empty description.
     * It will also mark or unmark the ToDo object respectively.
     *
     * @throws ZhongliException if the description of todo is empty.
     */
    private static ToDo parseToDo(String input, Boolean isDone) throws ZhongliException {
        ToDo toDo = parseToDo(formatTextToExcludeIsMark(input, isDone));

        if (isDone) {
            toDo.markDone();
        }

        return toDo;
    }

    /**
     * Transform the string input from user input into a valid deadline object.
     * It will check the format of the string is correct (Inclusive of a "/by" phrase).
     * It will check that both the description and endTime are not empty.
     * It will check that endTime can be converted into a LocalDate Object.
     *
     * @throws ZhongliException if the input does not follow the format.
     */
    public static Deadline parseDeadline(String input) throws ZhongliException {
        String[] descriptionArr = splitStringIntoTwo(input, "deadline", "Missing Description of Deadline");
        String[] deadlineArr = splitStringIntoTwo(descriptionArr[1], "/by", "Missing /by command");

        String deadline = deadlineArr[0].trim();
        checkStringIsEmpty(deadline, "Description cannot be empty");

        String endTime = deadlineArr[1].trim();
        checkStringIsEmpty(endTime, "End Time cannot be empty");

        LocalDate endTimeDate = Parser.parseDate(endTime);

        return new Deadline(deadline, endTimeDate);
    }

    /**
     * Transform the string input from text file into a valid deadline object.
     * It will check the format of the string is correct (Inclusive of a "/by" phrase).
     * It will check that both the description and endTime are not empty.
     * It will check that endTime can be converted into a LocalDate Object.
     * It will then mark/unmark the deadline object respectively.
     *
     * @throws ZhongliException if the input does not follow the format.
     */
    private static Deadline parseDeadline(String input, boolean isDone) throws ZhongliException {
        Deadline deadline = parseDeadline(formatTextToExcludeIsMark(input, isDone));

        if (isDone) {
            deadline.markDone();
        }

        return deadline;
    }

    /**
     * Transform the string input from user input into a valid event object.
     * It will check the format of the string is correct (Inclusive of a "/from" and "/to" phrase).
     * It will check that the description, startTime and endTime are not empty.
     * It will check that both startTime and endTime can be converted into a LocalDate Object.
     *
     * @throws ZhongliException if the input does not follow the format.
     */
    public static Event parseEvent(String input) throws ZhongliException {
        String[] eventArr = splitStringIntoTwo(input, "event", "Missing Description of Event");
        String[] fromArr = splitStringIntoTwo(eventArr[1], "/from", "Missing /from command");
        String[] toArr = splitStringIntoTwo(fromArr[1], "/to", "Missing /to command");

        String description = fromArr[0].trim();
        checkStringIsEmpty(description, "Description cannot be empty");

        String startTime = toArr[0].trim();
        checkStringIsEmpty(startTime, "Start Time cannot be empty");

        LocalDate startTimeDate = Parser.parseDate(startTime);

        String endTime = toArr[1].trim();
        checkStringIsEmpty(endTime, "End Time cannot be empty");

        LocalDate endTimeDate = Parser.parseDate(endTime);

        return new Event(description, startTimeDate, endTimeDate);
    }

    /**
     * Transform the string input from the text file into a valid event object.
     * It will check the format of the string is correct (Inclusive of a "/from" and "/to" phrase).
     * It will check that the description, startTime and endTime are not empty.
     * It will check that both startTime and endTime can be converted into a LocalDate Object.
     * It will then mark/unmark the event object respectively.
     *
     * @throws ZhongliException if the input does not follow the format.
     */
    private static Event parseEvent(String input, boolean isDone) throws ZhongliException {
        Event event = parseEvent(formatTextToExcludeIsMark(input, isDone));

        if (isDone) {
            event.markDone();
        }

        return event;
    }

    /**
     * Transform the String command into its respective Command class.
     *
     * @param command - String of command entered by the user.
     * @return An implementation of the abstract command class with a method run(TaskList, Ui , Storage).
     */
    public static Command parseCommand(
            String command, AliasList aliasList, AliasStorage aliasStorage,
            ProductList productList) {
        String firstWord = command.split(" ")[0];
        CommandType type = CommandType.fromString(firstWord);

        if (type.equals(CommandType.UNKNOWN)) {
            try {
                Alias aliasCommand = aliasList.findAlias(firstWord);
                type = CommandType.fromString(aliasCommand.getOriginalCommand());
                command = aliasCommand.refactorCommandToOriginal(command);
            } catch (ZhongliException e) {
                type = CommandType.fromString("");
            }

        }

        return switch (type) {
        case LIST -> new ListTaskCommand();
        case TODO, EVENT, DEADLINE -> new AddTaskCommand(command);
        case MARK -> new MarkCommand(command);
        case UNMARK-> new UnmarkCommand(command);
        case DELETE -> new DeleteCommand(command);
        case BYE -> new ByeCommand();
        case FIND -> new FindCommand(command);
        case HELP -> new HelpCommand();
        case UNKNOWN -> new WrongCommand(command);
        case ALIAS -> new AliasCommand(command, aliasList, aliasStorage);
        case ADDPRODUCT -> new AddProductCommand(command, productList);
        default -> new WrongCommand(command);
        };
    }
}
