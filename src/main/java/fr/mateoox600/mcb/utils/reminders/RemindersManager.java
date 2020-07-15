package fr.mateoox600.mcb.utils.reminders;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RemindersManager {

    public final List<Reminder> reminders;
    private final File remindersSaveFile;

    public RemindersManager() throws IOException {
        reminders = new ArrayList<>();
        remindersSaveFile = new File(MCB.config.getDataFolder(), "reminders.txt");
        if (!remindersSaveFile.exists()) remindersSaveFile.createNewFile();
        load();
        save();
        Timer timer = new Timer();
        timer.schedule(new RMManagerTask(), 1000 * 30, 1000 * 30);
    }

    public static long parseReminderTime(String arg) {
        long totalTime = 0;

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

        return totalTime;
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
        reminders.add(new Reminder(in, member.getUser(), channel, text, message));
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            reminders.add(Reminder.getReminderBySaveMsg(line));
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