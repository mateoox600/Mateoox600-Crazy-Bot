package fr.mateoox600.mcb.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.List;

public class StatsCommand extends Command {

    public StatsCommand() {
        this.name = "stats";
        this.help = "Give bot stats";
    }

    @Override
    protected void execute(CommandEvent e) {
        int onlineUsers = 0;
        int offlineUsers = 0;
        List<String> alreadyLooped = new ArrayList<>();
        for (Guild g : e.getJDA().getGuilds()) {
            for (Member user : g.getMembers()) {
                if (!user.getUser().isBot() && !alreadyLooped.contains(user.getId())) {
                    alreadyLooped.add(user.getId());
                    if (user.getOnlineStatus() == OnlineStatus.ONLINE || user.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) onlineUsers++;
                    else offlineUsers++;
                }
            }
        }
        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Mateoox600 Crazy Bot").setFooter("Bot creator: Mateoox600#9473", e.getJDA().getUserById(MCB.config.getOwnerId()).getEffectiveAvatarUrl())
                .addField("*General Stats*", "*Guilds*: " + e.getJDA().getGuilds().size(), true)
                .addField("*Channels:* " + jdaChannels(e.getJDA()), "*Categories*: " + e.getJDA().getCategories().size() + "\n*Text Channels*: " + e.getJDA().getTextChannels().size() + "\n*Voice Channels*: " + e.getJDA().getVoiceChannels().size(), true)
                .addField("*Users:* " + alreadyLooped.size(), "*Online Users*: " + onlineUsers + "\n*Offline Users*: " + offlineUsers, true);
        e.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    private int jdaChannels(JDA jda) {
        return jda.getPrivateChannels().size() + jda.getStoreChannels().size() + jda.getVoiceChannels().size() + jda.getTextChannels().size() + jda.getCategories().size();
    }

}
