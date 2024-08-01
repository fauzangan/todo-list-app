package todo_list_app.todo_list_app.utils.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateFormatter {
    public static Date convertStringToDate(String dateString, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Format tanggal tidak valid: " + e.getMessage());
            return null;
        }
    }

    public static String convertDateToString(Date date, String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
