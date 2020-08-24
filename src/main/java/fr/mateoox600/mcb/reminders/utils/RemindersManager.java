package fr.mateoox600.mcb.reminders.utils;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemindersManager {

    public final List<Reminder> reminders;
    private final File remindersSaveFile;

    public RemindersManager() throws IOException {
        reminders = new ArrayList<>();
        remindersSaveFile = new File(MCB.config.getDataFolder(), "reminders.txt");
        if (!remindersSaveFile.exists()) remindersSaveFile.createNewFile();
        load();
        Timer timer = new Timer();
        timer.schedule(new RMManagerTask(), 1000 * 60, 1000 * 60);
    }

    public static long parseReminderTime(String arg) {
        int days = 0, hours = 0, minutes = 0, seconds = 0;

        Matcher mat = Pattern.compile("(\\d+)(st|nd|rd|th)").matcher(arg);

        if (mat.find()) {
            int day = Integer.parseInt(mat.group().substring(0, mat.group().length()-2));
            Calendar currentTime = Calendar.getInstance();
            if (day > Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) day = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            if (day <= currentTime.get(Calendar.DAY_OF_MONTH)){
                days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) - currentTime.get(Calendar.DAY_OF_MONTH)+day-1;
            }else{
                days = day-currentTime.get(Calendar.DAY_OF_MONTH)-1;
            }
            hours = 24 - currentTime.get(Calendar.HOUR_OF_DAY);
            minutes = 60 - currentTime.get(Calendar.MINUTE);
            seconds = 60 - currentTime.get(Calendar.SECOND);
        }

        mat = Pattern.compile("\\d{1,}d").matcher(arg);
        if (mat.find()) days += Integer.parseInt(mat.group().substring(0, mat.group().length() - 1));
        mat = Pattern.compile("\\d{1,}h").matcher(arg);
        if (mat.find()) hours += Integer.parseInt(mat.group().substring(0, mat.group().length() - 1));
        mat = Pattern.compile("\\d{1,}m").matcher(arg);
        if (mat.find()) minutes += Integer.parseInt(mat.group().substring(0, mat.group().length() - 1));
        mat = Pattern.compile("\\d{1,}s").matcher(arg);
        if (mat.find()) seconds += Integer.parseInt(mat.group().substring(0, mat.group().length() - 1));

        long totalHours = (days * 24) + hours;
        long totalMinutes = (totalHours * 60) + minutes;
        long totalSeconds = (totalMinutes * 60) + seconds;

        return totalSeconds * 1000;
        /*long totalTime = 0;

        StringBuilder loopingOn = new StringBuilder();
        for (char arg_char : arg.toLowerCase().toCharArray()) {
            if (arg_char == 'd') {
                totalTime += Integer.parseInt(loopingOn.toString()) * 24 * 60 * 60 * 1000;
                loopingOn = new StringBuilder();
            } else if (arg_char == 'h') {
                totalTime += Integer.parseInt(loopingOn.toString()) * 60 * 60 * 1000;
                loopingOn = new StringBuilder();
            } else if (arg_char == 'm') {
                totalTime += Integer.parseInt(loopingOn.toString()) * 60 * 1000;
                loopingOn = new StringBuilder();
            } else if (arg_char == 's') {
                totalTime += Integer.parseInt(loopingOn.toString()) * 1000;
                loopingOn = new StringBuilder();
            } else loopingOn.append(arg_char);
        }

        return totalTime;*/
    }

    public static String findTimeIn(String message) {
        for (String words : message.split(" ")) {
            try {
                long time = RemindersManager.parseReminderTime(words);
                if (time > 0) {
                    return words;
                }
            } catch (Exception ignored) {
            }
        }
        return "0s";
    }

    public List<Reminder> getAllMemberReminders(Member member) {
        List<Reminder> found = new ArrayList<>();
        for (Reminder reminder : reminders) {
            if (reminder.getUser().getId().equals(member.getId())) found.add(reminder);
        }
        return found;
    }

    public void addReminder(String text, Member member, TextChannel channel, long in, boolean message) {
        Reminder reminder = new Reminder(in, member.getUser(), channel, text, message);
        reminders.add(reminder);
        reminder.start();
        System.out.println("[INFO] Reminder launch: " + reminder.getText() + " for " + reminder.getUser() + " from " + reminder.getChannel().getGuild().getName());
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        reminder.start();
        System.out.println("[INFO] Reminder launch: " + reminder.getText() + " for " + reminder.getUser().getName() + " from " + reminder.getChannel().getGuild().getName());
    }

    public void save() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(remindersSaveFile));
        for (Reminder rm : reminders) {
            bufferedWriter.write(rm.getSaveMsg() + "\n");
        }
        bufferedWriter.close();
    }

    public void load() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(remindersSaveFile));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Reminder reminder = Reminder.getReminderBySaveMsg(line);
            addReminder(reminder);
        }
        bufferedReader.close();
    }
}

class RMManagerTask extends TimerTask {

    @Override
    public void run() {
        try {
            MCB.remindersManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}