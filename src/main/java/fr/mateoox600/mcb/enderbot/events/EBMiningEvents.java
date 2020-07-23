package fr.mateoox600.mcb.enderbot.events;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Timer;

public class EBMiningEvents extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        if (e.getAuthor().getId().equals("280726849842053120") && !e.getMessage().getEmbeds().isEmpty()) {

            if (e.getMessage().getEmbeds().isEmpty() || e.getMessage().getEmbeds().size() < 1) return;

            MessageEmbed msgEmbed = e.getMessage().getEmbeds().get(0);
            if (msgEmbed.getFields().isEmpty() || msgEmbed.getFields().size() < 1) return;

            MessageEmbed.Field miningField = msgEmbed.getFields().get(0);
            if (!miningField.getName().contains("Ressources") && !miningField.getName().contains("Resources mined")) return;

            e.getMessage().addReaction(MCB.reactionNumber[0]).queue(emoji -> MCB.reactionsEventMessage.put(e.getMessage().getId(), e.getMessage()));
        }
    }


    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent e) {

        Message message = MCB.reactionsEventMessage.get(e.getMessageId());
        if (message == null) return;
        Member member = null;
        MessageHistory messageHistory = e.getChannel().getHistoryBefore(message, 10).complete();
        for (Message mHistory : messageHistory.getRetrievedHistory()) {
            if (mHistory.getContentRaw().startsWith(">mi") || mHistory.getContentRaw().startsWith(">mine")) {
                member = mHistory.getMember();
                break;
            }
        }
        if (member == null) return;
        if (!e.getUser().getId().equals(member.getId())) return;
        if (e.getUser().getId().equals(e.getJDA().getSelfUser().getId())) return;
        if (message.getEmbeds().isEmpty() || message.getEmbeds().size() < 1) return;

        MessageEmbed msgEmbed = message.getEmbeds().get(0);
        if (msgEmbed.getFields().isEmpty() || msgEmbed.getFields().size() < 1) return;

        MessageEmbed.Field miningField = msgEmbed.getFields().get(0);
        if (!miningField.getName().contains("Ressources") && !miningField.getName().contains("Resources mined")) return;

        // TODO: 10/07/2020 add taxe

        if (e.getReactionEmote().getName().equals(MCB.reactionNumber[0])) {

            StringBuilder trade = new StringBuilder(">trade");
            for (String ress : miningField.getValue().split("\n"))
                trade.append(" ").append(ress.split(":")[1]).append(" ").append(Long.valueOf(String.join("", ress.split(":")[3].split(" "))));
            trade.append(" @");
            e.getChannel().sendMessage("```" + trade.toString() + "```").queue(msg -> MCB.reactionsEventMessage.remove(e.getMessageId()));

        }

    }

}
