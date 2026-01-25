package Parser;
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

}
