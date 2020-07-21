package fr.mateoox600.mcb.events;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class BinaryEvent extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent e) {

        Message message = MCB.reactionsEventMessage.get(e.getMessageId());
        if (message == null) return;

        if (message.getContentRaw().contains("Encode or Decode (E/D):")) {
            if (!e.getUser().getId().equals(message.getContentRaw().split(" ")[0].substring(2, message.getContentRaw().split(" ")[0].length() - 1)))
                return;
            if (e.getReactionEmote().getName().equals("\uD83C\uDDEA")) {
                String[] args = message.getContentRaw().substring(29 + message.getContentRaw().split(" ")[0].length(), message.getContentRaw().length() - 3).split("\\s+");
                try {
                    StringBuilder output = new StringBuilder();
                    for (char c : String.join(" ", args).toLowerCase().toCharArray())
                        output.append(Integer.toBinaryString(c)).append(" ");
                    message.delete().queue(msg -> e.getChannel().sendMessage("<@" + message.getAuthor().getId() + "> ```" + output.toString() + "```").queue(msg1 -> MCB.reactionsEventMessage.remove(e.getMessageId())));
                } catch (Exception error) {
                    error.printStackTrace();
                    e.getChannel().sendMessage("```ERROR: Can't parse those words```").queue();
                }
            } else if (e.getReactionEmote().getName().equals("\uD83C\uDDE9")) {
                String[] args = message.getContentRaw().substring(29 + message.getContentRaw().split(" ")[0].length(), message.getContentRaw().length() - 3).split("\\s+");
                try {
                    StringBuilder output = new StringBuilder();
                    for (String str_n : args)
                        output.append((char) Integer.parseInt(str_n, 2));
                    message.delete().queue(msg -> e.getChannel().sendMessage("<@" + message.getAuthor().getId() + "> ```" + output.toString() + "```").queue(msg1 -> MCB.reactionsEventMessage.remove(e.getMessageId())));
                } catch (Exception error) {
                    e.getChannel().sendMessage("```ERROR: Can't parse those binary numbers```").queue();
                }
            }
        }

        // TODO: 06/06/2020 upgrade binary system 
    }

}
