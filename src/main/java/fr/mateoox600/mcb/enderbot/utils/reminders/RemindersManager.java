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

}