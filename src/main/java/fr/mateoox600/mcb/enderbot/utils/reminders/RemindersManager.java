package fr.mateoox600.mcb.enderbot.utils.reminders;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class RemindersManager {

    public final List<Reminder> reminders;

    public RemindersManager() {
        reminders = new ArrayList<>();
    }

    public void addReminder(String text, Member member, TextChannel channel, long in) {
        reminders.add(new Reminder(in, member, channel, text));
    }

    public static long parseReminderTime(String arg){
        long totalTime = 0;

        String loopingOn = "";
        for (char arg_char : arg.toCharArray()){
            if (arg_char == 'D' || arg_char == 'd') {
                totalTime += Integer.parseInt(loopingOn)*24*60*60*1000;
                loopingOn = "";
            }
            else if (arg_char == 'H' || arg_char == 'h') {
                totalTime += Integer.parseInt(loopingOn)*60*60*1000;
                loopingOn = "";
            }
            else if (arg_char == 'M' || arg_char == 'm') {
                totalTime += Integer.parseInt(loopingOn)*60*1000;
                loopingOn = "";
            }
            else if (arg_char == 'S' || arg_char == 's') {
                totalTime += Integer.parseInt(loopingOn)*1000;
                loopingOn = "";
            }
            else loopingOn += arg_char;
        }

        return totalTime;
    }

}