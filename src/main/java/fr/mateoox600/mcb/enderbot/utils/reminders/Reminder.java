package fr.mateoox600.mcb.enderbot.utils.reminders;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder {

    private final Member member;
    private final TextChannel channel;
    private final String text;

    public Reminder(long in, Member member, TextChannel channel, String text) {
        this.member = member;
        this.channel = channel;
        this.text = text;
        channel.sendMessage(parseMessageTime(in)).queue();
        Timer timer = new Timer();
        timer.schedule(new Task(this), new Date(in + System.currentTimeMillis()));
    }

    private String getMessage() {
        return "<@" + member.getId() + "> Reminder: " + text;
    }

    public void sendReminder() {
        channel.sendMessage(getMessage()).queue();
    }

    private String parseMessageTime(long in){
        StringBuilder stringBuilder = new StringBuilder("Set a reminder in ");
        int days = Math.toIntExact(in / 1000 / 60 / 60 / 24);
        if (days > 0) {
            if (days > 1) {
                stringBuilder.append(days).append(" hours, ");
            }else{
                stringBuilder.append(days).append(" hours, ");
            }
        }
        in -= days*24*60*60*1000;
        int hours = Math.toIntExact(in / 1000 / 60 / 60);
        if (hours > 0) {
            if (hours > 1) {
                stringBuilder.append(hours).append(" hours, ");
            }else{
                stringBuilder.append(hours).append(" hours, ");
            }
        }
        in -= hours*60*60*1000;
        int minutes = Math.toIntExact(in/1000/60);
        if (minutes > 0) {
            if (minutes > 1) {
                stringBuilder.append(minutes).append(" minutes, ");
            }else{
                stringBuilder.append(minutes).append(" minute, ");
            }
        }
        in -= minutes*60*1000;
        int seconds = Math.toIntExact(in/1000);
        if (seconds > 0) {
            if (seconds > 1) {
                stringBuilder.append(seconds).append(" seconds ");
            }else{
                stringBuilder.append(seconds).append(" second ");
            }
        }
        stringBuilder.append("from now");
        return stringBuilder.toString();
    }

}

class Task extends TimerTask{

    private Reminder reminder;

    public Task(Reminder reminder){
        this.reminder = reminder;
    }

    @Override
    public void run() {
        reminder.sendReminder();
    }

}
