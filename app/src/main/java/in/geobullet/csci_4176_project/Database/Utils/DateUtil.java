package in.geobullet.csci_4176_project.Database.Utils;

import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;


/**
 * Created by Nick on 2017-03-23.
 */

public class DateUtil {

    private static SimpleDateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT);
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date getDateValueFromColumn(String columnVal) throws java.text.ParseException {
        Calendar t = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT, Locale.getDefault());
        return sdf.parse(columnVal);
    }

    public static String formatDate(Date d) {
        return df.format(d);
    }

    public static String formatTime(Date d) {
        return timeFormat.format(d);
    }
}
