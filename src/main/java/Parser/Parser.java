package Parser;
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

}
