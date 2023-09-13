package pro.edu.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    private static final String Pattern = "dd/MM/yyyy";

    public static Date toDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Pattern);
            return sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String toString(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Pattern);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
