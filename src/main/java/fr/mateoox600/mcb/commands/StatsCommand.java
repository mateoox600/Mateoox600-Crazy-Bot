package fr.mateoox600.mcb.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class StatsCommand extends Command {

    public StatsCommand() {
        this.name = "stats";
        this.help = "Give bot stats";
    }

    @Override
    protected void execute(CommandEvent e) {
        int onlineUsers = 0;
        int offlineUsers = 0;
        for (Guild g : e.getJDA().getGuilds()) {
            for (Member user : g.getMembers()) {
                if (user.getUser().isBot()) {
                    if (user.getOnlineStatus() == OnlineStatus.ONLINE) onlineUsers++;
                    else offlineUsers++;
                }
            }
        }
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Mateoox600 Crazy Bot")
                .addField("*General Stats*", "*Guilds*: " + e.getJDA().getGuilds().size(), true)
                .addField("*Channels:* " + jdaChannels(e.getJDA()), "*Categories*: " + e.getJDA().getCategories().size() + "\n*Text Channels*: " + e.getJDA().getTextChannels().size() + "\n*Voice Channels*: " + e.getJDA().getVoiceChannels().size(), true)
                .addField("*Users:* " + e.getJDA().getUsers().size(), "*Online Users*: " + onlineUsers + "\n*Offline Users*: " + offlineUsers, true);
        e.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    private int jdaChannels(JDA jda) {
        return jda.getPrivateChannels().size() + jda.getStoreChannels().size() + jda.getVoiceChannels().size() + jda.getTextChannels().size() + jda.getCategories().size();
    }

}
