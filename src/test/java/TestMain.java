import java.util.Calendar;

public class TestMain {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        StringBuilder binaryOutput = new StringBuilder(Long.toBinaryString(Long.parseLong("283532530617942016")));
        int oLength = binaryOutput.length();
        for (int i = 0; i < 64-oLength; i++) binaryOutput.insert(0, "0");
        calendar.setTimeInMillis(Long.parseLong(binaryOutput.substring(0, binaryOutput.length()-22), 2) + 1420070400000L);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));

    }

}
