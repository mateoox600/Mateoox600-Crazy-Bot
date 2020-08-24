import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

    public static void main(String[] args) {

        while (true) {
            int days = 0, hours = 0, minutes = 0, seconds = 0;

            Matcher mat = Pattern.compile("(\\d+)(st|nd|rd|th)").matcher(new Scanner(System.in).nextLine());

            if (mat.find()) {
                int day = Integer.parseInt(mat.group().substring(0, mat.group().length() - 2));
                Calendar currentTime = Calendar.getInstance();
                if (day > Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH))
                    day = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                if (day <= currentTime.get(Calendar.DAY_OF_MONTH)) {
                    days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) - currentTime.get(Calendar.DAY_OF_MONTH) + day - 1;
                } else {
                    days = day - currentTime.get(Calendar.DAY_OF_MONTH) - 1;
                }
                hours = 24 - currentTime.get(Calendar.HOUR_OF_DAY);
                minutes = 60 - currentTime.get(Calendar.MINUTE);
                seconds = 60 - currentTime.get(Calendar.SECOND);
            }

            System.out.println(days);
            System.out.println(hours);
            System.out.println(minutes);
            System.out.println(seconds);
        }

    }

}
