package fr.mateoox600.mcb.utils.reminders;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder {

    private final User user;
    private final TextChannel channel;
    private final String text;
    private final long end, in;
    public Timer timer;

    public Reminder(long in, User user, TextChannel channel, String text, boolean message) {
        this.user = user;
        this.channel = channel;
        this.text = text;
        this.in = in;
        this.end = in + System.currentTimeMillis();
        if (message) channel.sendMessage(parseMessageTime(in)).queue();
    }

    public static Reminder getReminderBySaveMsg(String msg) {
        String[] args = msg.split("/");
        return new Reminder(Long.parseLong(args[0]) - System.currentTimeMillis(), MCB.jda.getUserById(args[2]), MCB.jda.getTextChannelById(args[3]), args[1], false);
    }

    public void start(){
        timer = new Timer();
        if (in <= 10*1000) {
            timer.schedule(new Task(this), new Date(10*1000 + System.currentTimeMillis()));
        } else timer.schedule(new Task(this), new Date(in + System.currentTimeMillis()));
    }

    public static String longTimeToStringTime(long in) {
        String string = "";

        in /= 1000; int days = (int) (in / 86400);
        in -= days * 86400; int hours = (int) (in / 3600);
        in -= hours * 3600; int minutes = (int) (in / 60);
        in -= minutes * 60;  int seconds = (int) in;

        if (days > 0) string += days + " day" + (days > 1 ? "s" : "") + ", ";
        if (hours > 0) string += hours + " hour" + (hours > 1 ? "s" : "") + ", ";
        if (minutes > 0) string += minutes + " minute" + (minutes > 1 ? "s" : "") + ", ";
        if (seconds > 0) string += seconds + " second" + (seconds > 1 ? "s" : "") + ", ";

        if(string.length() > 0) string = string.substring(0, string.length() - 2);
        return string;
    }

    private MessageEmbed getMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(user.getName(), null, user.getEffectiveAvatarUrl());
        embedBuilder.setColor(channel.getGuild().getMember(user).getRoles().get(0).getColor());
        embedBuilder.addField("Reminder:", text, false);
        return embedBuilder.build();
    }

    public void sendReminder() {
        channel.sendMessage(user.getAsMention()).queue(msg -> channel.sendMessage(getMessage()).queue());
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public String getSaveMsg() {
        return end + "/" + text + "/" + user.getId() + "/" + channel.getId();
    }

    private String parseMessageTime(long in) {
        return "Set a reminder in " + Reminder.longTimeToStringTime(in) + " from now";
    }

    public long getTime() {
        return end - System.currentTimeMillis();
    }
}

class Task extends TimerTask {

    private final Reminder reminder;

    public Task(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public void run() {
        reminder.sendReminder();
        MCB.remindersManager.reminders.remove(reminder);
    }

}
