package zhongli.parser;

import java.time.DateTimeException;
import java.time.LocalDate;

import zhongli.alias.Alias;
import zhongli.alias.AliasList;
import zhongli.command.AddProductCommand;
import zhongli.command.AddTaskCommand;
import zhongli.command.AliasCommand;
import zhongli.command.ByeCommand;
import zhongli.command.Command;
import zhongli.command.CommandType;
import zhongli.command.DeleteCommand;
import zhongli.command.DeleteProductCommand;
import zhongli.command.FindCommand;
import zhongli.command.HelpCommand;
import zhongli.command.ListProductCommand;
import zhongli.command.ListTaskCommand;
import zhongli.command.MarkCommand;
import zhongli.command.UnmarkCommand;
import zhongli.command.WrongCommand;
import zhongli.product.ProductList;
import zhongli.storage.AliasStorage;
import zhongli.storage.ProductStorage;
import zhongli.task.Deadline;
import zhongli.task.Event;
import zhongli.task.Task;
import zhongli.task.ToDo;
import zhongli.zhongliexception.ZhongliException;

/**
 * Parses various input formats into their corresponding object representations.
 * Handles parsing of dates, strings, tasks, and commands, with validation and error handling
 * for invalid formats. Supports special keywords and aliases for command flexibility.
 *
 */
public class Parser {

    /**
     * Parses a date string into a LocalDate object. Supports the special keyword "now"
     * which returns the current date, and standard ISO format (YYYY-MM-DD).
     *
     * @param dateText A string in ISO date format (YYYY-MM-DD) or the keyword "now".
     * @return A LocalDate object parsed from the input string.
     * @throws ZhongliException If the dateText does not match ISO format or any other valid format.
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
     * Splits a string into two parts at the first occurrence of the specified delimiter.
     *
     * @param input The string to split.
     * @param regex The delimiter to split by.
     * @param errorMsg The error message to display if the delimiter is not found.
     * @return An array of two elements: the substring before and after the delimiter.
     * @throws ZhongliException If the delimiter is not found in the input string.
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
     * Validates that a string is not empty, throwing an exception if it is.
     *
     * @param input The string to validate.
     * @param errorMsg The error message to throw if the string is empty.
     * @throws ZhongliException If the input string is empty.
     */
    private static void checkStringIsEmpty(String input, String errorMsg) throws ZhongliException {
        assert errorMsg != null : "errorMsg is null";
        if (input.isEmpty()) {
            throw new ZhongliException(errorMsg);
        }
    }

    /**
     * Parses a task from a text file line into a Task object. Determines the task type from
     * the first word and validates the presence of either "/mark" or "/unmark" indicators
     * to establish the task's completion status.
     *
     * @param line A line from the text file containing task data.
     * @return A Task object (ToDo, Deadline, or Event) parsed from the file.
     * @throws ZhongliException If the line format is invalid, task type is unrecognized,
     *                          or both/neither "/mark" and "/unmark" are present.
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
     * Parses user input into a Task object. Determines the task type from the first word
     * and delegates to the appropriate parsing method.
     *
     * @param line A string entered by the user containing task creation information.
     * @return A Task object (ToDo, Deadline, or Event) parsed from the input.
     * @throws ZhongliException If the task type is unrecognised or the input format is invalid.
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
     * Removes the "/mark" or "/unmark" indicator from a string by splitting at the first occurrence.
     *
     * @param input The string to process.
     * @param isDone Boolean flag indicating whether to remove "/mark" (true) or "/unmark" (false).
     * @return The input string with the mark/unmark indicator removed.
     */
    public static String formatTextToExcludeIsMark(String input, boolean isDone) {
        return isDone
                ? input.split("/mark")[0]
                : input.split("/unmark")[0];
    }

    /**
     * Parses user input into a ToDo object. Validates that a description is present and non-empty.
     *
     * @param input A string entered by the user in the format "todo <description/>"
     * @return A ToDo object created from the input.
     * @throws ZhongliException If the todo keyword is missing or the description is empty.
     */
    public static ToDo parseToDo(String input) throws ZhongliException {
        String[] toDoArr = splitStringIntoTwo(input, "todo", "Missing Description of ToDo");

        String description = toDoArr[1].trim();
        checkStringIsEmpty(description, "Description cannot be empty");

        return new ToDo(description);
    }

    /**
     * Parses a text file line into a ToDo object and sets its completion status.
     * Removes the mark/unmark indicator before parsing, then applies the appropriate completion state.
     *
     * @param input A line from the text file in the format "todo <description/> /mark" or "/unmark".
     * @param isDone Boolean indicating whether the task should be marked as complete.
     * @return A ToDo object with the completion status set accordingly.
     * @throws ZhongliException If the input format is invalid or the description is empty.
     */
    private static ToDo parseToDo(String input, Boolean isDone) throws ZhongliException {
        ToDo toDo = parseToDo(formatTextToExcludeIsMark(input, isDone));

        if (isDone) {
            toDo.markDone();
        }

        return toDo;
    }

    /**
     * Parses user input into a Deadline object. Validates the presence of the "/by" delimiter
     * and ensures both the description and deadline date are non-empty and correctly formatted.
     *
     * @param input A string entered by the user in the format "deadline <description/> /by <date/>".
     * @return A Deadline object created from the input.
     * @throws ZhongliException If the format is invalid, required fields are missing,
     *                          or the date cannot be parsed.
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
     * Parses a text file line into a Deadline object and sets its completion status.
     * Removes the mark/unmark indicator before parsing, then applies the appropriate completion state.
     *
     * @param input A line from the text file in the format "deadline <description/> /by <date/> /mark" or "/unmark".
     * @param isDone Boolean indicating whether the deadline should be marked as complete.
     * @return A Deadline object with the completion status set accordingly.
     * @throws ZhongliException If the format is invalid or required fields are missing.
     */
    private static Deadline parseDeadline(String input, boolean isDone) throws ZhongliException {
        Deadline deadline = parseDeadline(formatTextToExcludeIsMark(input, isDone));

        if (isDone) {
            deadline.markDone();
        }

        return deadline;
    }

    /**
     * Parses user input into an Event object. Validates the presence of "/from" and "/to" delimiters
     * and ensures the description, start time, and end time are all non-empty and correctly formatted.
     *
     * @param input A string entered by the user in the format "event <description/> /from <date/> /to <date/>".
     * @return An Event object created from the input.
     * @throws ZhongliException If the format is invalid, required fields are missing,
     *                          or the dates cannot be parsed.
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
     * Parses a text file line into an Event object and sets its completion status.
     * Removes the mark/unmark indicator before parsing, then applies the appropriate completion state.
     *
     * @param input A line from the text file in the format "event <description/> /from <date/> /to <date/> /mark"
     *              or "/unmark".
     * @param isDone Boolean indicating whether the event should be marked as complete.
     * @return An Event object with the completion status set accordingly.
     * @throws ZhongliException If the format is invalid or required fields are missing.
     */
    private static Event parseEvent(String input, boolean isDone) throws ZhongliException {
        Event event = parseEvent(formatTextToExcludeIsMark(input, isDone));

        if (isDone) {
            event.markDone();
        }

        return event;
    }

    /**
     * Parses a command string into the appropriate Command object. Identifies the command type
     * from the first word and handles command aliases by checking the alias list. Returns a
     * corresponding Command implementation or a WrongCommand if the command is unrecognised.
     *
     * @param command A string containing the command entered by the user.
     * @param aliasList The list of command aliases to resolve command shortcuts.
     * @param aliasStorage The storage object for persisting alias changes.
     * @param productList The list of products available in the application.
     * @param productStorage The storage object for persisting product changes.
     * @return A Command object representing the parsed command ready for execution.
     */
    public static Command parseCommand(
            String command, AliasList aliasList, AliasStorage aliasStorage,
            ProductList productList, ProductStorage productStorage) {
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
        case ADDPRODUCT -> new AddProductCommand(command, productList, productStorage);
        case LISTPRODUCT -> new ListProductCommand(productList);
        case DELETEPRODUCT -> new DeleteProductCommand(command, productList, productStorage);
        default -> new WrongCommand(command);
        };
    }
}
