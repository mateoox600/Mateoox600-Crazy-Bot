package fr.mateoox600.mcb.commands.manager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

public class CommandEvent {

    private final JDA jda;
    private final Message message;
    private final MessageChannel channel;
    private final TextChannel textChannel;
    private final User author;
    private final Member member;
    private final Guild guild;

    public CommandEvent(JDA jda, Message message, MessageChannel channel, TextChannel textChannel, User author, Member member, Guild guild){
        this.jda = jda;
        this.message = message;
        this.channel = channel;
        this.author = author;
        this.member = member;
        this.guild = guild;
        this.textChannel = textChannel;
    }

    public JDA getJDA() {
        return jda;
    }

    public Message getMessage() {
        return message;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public User getAuthor() {
        return author;
    }

    public Member getMember() {
        return member;
    }

    public Guild getGuild() {
        return guild;
    }

}
