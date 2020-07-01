package fr.mateoox600.mcb.enderbot.utils.reminders;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class RemindersManager {

    public final List<Reminder> reminders;
    private final File remindersSaveFile;

    public RemindersManager() throws IOException {
        reminders = new ArrayList<>();
        remindersSaveFile = new File(MCB.config.getDataFolder(), "reminders.txt");
        if (!remindersSaveFile.exists()) remindersSaveFile.createNewFile();
        load();
    }

    public void addReminder(String text, Member member, TextChannel channel, long in) {
        reminders.add(new Reminder(in, member.getUser(), channel, text));
    }

    public static long parseReminderTime(String arg){
        long totalTime = 0;

        StringBuilder loopingOn = new StringBuilder();
        for (char arg_char : arg.toLowerCase().toCharArray()){
            if (arg_char == 'd') {
                totalTime += Integer.parseInt(loopingOn.toString())*24*60*60*1000;
                loopingOn = new StringBuilder();
            }else if (arg_char == 'h') {
                totalTime += Integer.parseInt(loopingOn.toString())*60*60*1000;
                loopingOn = new StringBuilder();
            }else if (arg_char == 'm') {
                totalTime += Integer.parseInt(loopingOn.toString())*60*1000;
                loopingOn = new StringBuilder();
            }else if (arg_char == 's') {
                totalTime += Integer.parseInt(loopingOn.toString())*1000;
                loopingOn = new StringBuilder();
            }else loopingOn.append(arg_char);
        }

        return totalTime;
    }

    public void save() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(remindersSaveFile));
        for (Reminder rm : reminders){
            bufferedWriter.write(rm.getSaveMsg() + "\n");
        }
    }

    public void load() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(remindersSaveFile));
        String line;
        while ((line = bufferedReader.readLine()) != null)
            reminders.add(Reminder.getReminderBySaveMsg(line));
    }
}

}