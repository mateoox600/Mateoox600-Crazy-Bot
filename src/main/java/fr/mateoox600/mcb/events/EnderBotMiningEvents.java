package fr.mateoox600.mcb.events;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class EnderBotMiningEvents extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        if (e.getAuthor().getId().equals("280726849842053120") && e.getChannel().getId().equals("722010652213772379") && !e.getMessage().getEmbeds().isEmpty()) {
            MessageEmbed msg_embed = e.getMessage().getEmbeds().get(0);
            try {
                if (msg_embed.getAuthor().getName().equalsIgnoreCase(e.getJDA().getUserById(MCB.config.getOwnerId()).getName())) {
                    StringBuilder trade = new StringBuilder(">trade");
                    MessageEmbed.Field field = msg_embed.getFields().get(0);
                    for (String ress : field.getValue().split("\n"))
                        trade.append(" ").append(ress.split(":")[1]).append(" ").append(Long.valueOf(String.join("", ress.split(":")[3].split(" "))));
                    trade.append(" @");
                    e.getChannel().sendMessage("```" + trade.toString() + "```").queue();
                }
            }catch (Exception ignored){
            }
        }
    }
}
