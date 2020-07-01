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

    private final User member;
    private final TextChannel channel;
    private final String text;
    private final long end;

    public Reminder(long in, User member, TextChannel channel, String text, boolean message) {
        this.member = member;
        this.channel = channel;
        this.text = text;
        if (message) channel.sendMessage(parseMessageTime(in)).queue();
        Timer timer = new Timer();
        end = in + System.currentTimeMillis();
        if (in <= 0) timer.schedule(new Task(this), new Date(System.currentTimeMillis() + 1));
        else timer.schedule(new Task(this), new Date(in + System.currentTimeMillis()));
    }

    private MessageEmbed getMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(member.getName(), null, member.getEffectiveAvatarUrl());
        embedBuilder.setColor(channel.getGuild().getMember(member).getRoles().get(0).getColor());
        embedBuilder.addField("Reminder:", text, false);
        return embedBuilder.build();
    }

    public void sendReminder() {
        channel.sendMessage(member.getAsMention()).queue(msg -> channel.sendMessage(getMessage()).queue());
    }

    public String getSaveMsg() {
        return end + "/" + text + "/" + member.getId() + "/" + channel.getId();
    }

    public static Reminder getReminderBySaveMsg(String msg) {
        String[] args = msg.split("/");
        return new Reminder(Long.parseLong(args[0]) - System.currentTimeMillis(), MCB.jda.getUserById(args[2]), MCB.jda.getTextChannelById(args[3]), args[1], false);
    }

    private String parseMessageTime(long in) {
        StringBuilder stringBuilder = new StringBuilder("Set a reminder in ");
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
        stringBuilder.append("from now");
        return stringBuilder.toString();
    }

}

class Task extends TimerTask {

    private Reminder reminder;

    public Task(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public void run() {
        reminder.sendReminder();
        MCB.remindersManager.reminders.remove(reminder);
    }

}
