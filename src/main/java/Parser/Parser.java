package Parser;
import Task.Deadline;
import Task.ToDo;
import ZhongliException.ZhongliException;

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

    private static String[] splitStringIntoTwo(String input, String regex, String errorMsg) throws ZhongliException {
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

    public static ToDo parseToDo(String input) throws ZhongliException {
        String[] toDoArr = splitStringIntoTwo(input, "todo", "Missing Description of ToDo");
        String description = toDoArr[1].trim();
        checkStringIsEmpty(description, "Description cannot be empty");
        return new ToDo(description);
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

}
