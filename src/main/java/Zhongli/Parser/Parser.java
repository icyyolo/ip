package Zhongli.Parser;
import Zhongli.Task.*;
import Zhongli.Command.*;
import Zhongli.ZhongliException.ZhongliException;

import java.time.*;

public class Parser {

    public static LocalDate parseDate(String dateText) throws ZhongliException {
        // Special cases:
        if (dateText.equals("now")) {
            return LocalDate.now();
        }

        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeException e) {
            throw new ZhongliException(e.getMessage() +
                    "\nDate Should be in this format YYYY-MM-DD");
        }
    }

    public static String[] splitStringIntoTwo(String input, String regex, String errorMsg) throws ZhongliException {
        String[] res = input.split(regex, 2);
        if (res.length < 2) {
            throw new ZhongliException(errorMsg);
        }
        return res;
    }

    private static void checkStringIsEmpty(String input, String errorMsg) throws ZhongliException {
        if (input.isEmpty()) {
            throw new ZhongliException(errorMsg);
        }
    }

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

    public static Task parseTaskFromInput(String line) throws ZhongliException {
        String typeOfTask = line.split(" ")[0].toLowerCase();
        return switch (typeOfTask) {
            case ("todo") -> Parser.parseToDo(line);
            case ("deadline") -> Parser.parseDeadline(line);
            case ("event") -> Parser.parseEvent(line);
            default -> throw new ZhongliException("task type not found");
        };
    }

    public static String formatTextToExcludeIsMark(String input, boolean isDone) {
        return isDone
                ? input.split("/mark")[0]
                : input.split("/unmark")[0];
    }

    public static ToDo parseToDo(String input) throws ZhongliException {
        String[] toDoArr = splitStringIntoTwo(input, "todo", "Missing Description of ToDo");
        String description = toDoArr[1].trim();
        checkStringIsEmpty(description, "Description cannot be empty");
        return new ToDo(description);
    }

    private static ToDo parseToDo(String input, Boolean isDone) throws ZhongliException {
        ToDo toDo = parseToDo(formatTextToExcludeIsMark(input, isDone));
        if (isDone) {
            toDo.markDone();
        }
        return toDo;
    }

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

    private static Deadline parseDeadline(String input, boolean isDone) throws ZhongliException {
        Deadline deadline = parseDeadline(formatTextToExcludeIsMark(input, isDone));
        if (isDone) {
            deadline.markDone();
        }
        return deadline;
    }

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

    private static Event parseEvent(String input, boolean isDone) throws ZhongliException {
        Event event = parseEvent(formatTextToExcludeIsMark(input, isDone));
        if (isDone) {
            event.markDone();
        }
        return event;
    }

    public static Command parseCommand(String command) {
        String firstWord = command.split(" ")[0];
        return switch (firstWord) {
            case "list" -> new ListTaskCommand();
            case "todo", "event", "deadline" -> new AddTaskCommand(command);
            case "mark" -> new MarkCommand(command);
            case "unmark" -> new UnmarkCommand(command);
            case "delete" -> new DeleteCommand(command);
            case "bye" -> new ByeCommand();
            case "find" -> new FindCommand(command);
            default -> new WrongCommand();
        };
    }
}
