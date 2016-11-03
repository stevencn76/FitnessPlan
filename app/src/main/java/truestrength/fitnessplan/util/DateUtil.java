package truestrength.fitnessplan.util;



import android.util.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by steven on 31/10/16.
 */

public class DateUtil {
    public static long diffDays(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime() + (24 * 3600 * 1000);
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static long diffDays(String startDate, String endDate) {
        long diff = toDate(endDate).getTime() - toDate(startDate).getTime() + (24 * 3600 * 1000);
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date toDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");

        return format.format(date);
    }

    public static String toDateAndWeekdayString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy [ EEE ]");

        return format.format(date);
    }
}
