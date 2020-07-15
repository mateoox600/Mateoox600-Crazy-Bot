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
    private final long end;

    public Reminder(long in, User user, TextChannel channel, String text, boolean message) {
        this.user = user;
        this.channel = channel;
        this.text = text;
        if (message) channel.sendMessage(parseMessageTime(in)).queue();
        Timer timer = new Timer();
        end = in + System.currentTimeMillis();
        if (in <= 0) timer.schedule(new Task(this), new Date(System.currentTimeMillis() + 1));
        else timer.schedule(new Task(this), new Date(in + System.currentTimeMillis()));
    }

    public static Reminder getReminderBySaveMsg(String msg) {
        String[] args = msg.split("/");
        return new Reminder(Long.parseLong(args[0]) - System.currentTimeMillis(), MCB.jda.getUserById(args[2]), MCB.jda.getTextChannelById(args[3]), args[1], false);
    }

    public static String longTimeToStringTime(long in) {
        StringBuilder stringBuilder = new StringBuilder();
        int days = Math.toIntExact(in / 1000 / 60 / 60 / 24);
        if (days > 0) {
            if (days > 1) {
                stringBuilder.append(days).append(" days, ");
            } else {
                stringBuilder.append(days).append(" day, ");
            }
        }
        in -= days * 24 * 60 * 60 * 1000;
        int hours = Math.toIntExact(in / 1000 / 60 / 60);
        if (hours > 0) {
            if (hours > 1) {
                stringBuilder.append(hours).append(" hours, ");
            } else {
                stringBuilder.append(hours).append(" hour, ");
            }
        }
        in -= hours * 60 * 60 * 1000;
        int minutes = Math.toIntExact(in / 1000 / 60);
        if (minutes > 0) {
            if (minutes > 1) {
                stringBuilder.append(minutes).append(" minutes, ");
            } else {
                stringBuilder.append(minutes).append(" minute, ");
            }
        }
        in -= minutes * 60 * 1000;
        int seconds = Math.toIntExact(in / 1000);
        if (seconds > 0) {
            if (seconds > 1) {
                stringBuilder.append(seconds).append(" seconds ");
            } else {
                stringBuilder.append(seconds).append(" second ");
            }
        }
        return stringBuilder.toString();
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

    public String getSaveMsg() {
        return end + "/" + text + "/" + user.getId() + "/" + channel.getId();
    }

    private String parseMessageTime(long in) {
        return "Set a reminder in " + Reminder.longTimeToStringTime(in) + "from now";
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
