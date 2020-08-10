package fr.mateoox600.mcb.bots.badmintonbot.events;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class BadBotMessageEvents extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        // check message author is BadmintonBot
        if (e.getAuthor().getId().equals("681191706326204426")) {

            // check if there is an embed in the message
            if (e.getMessage().getEmbeds().size() > 0) {

                // check the title of the embed is a match embed
                if (e.getMessage().getEmbeds().get(0).getAuthor().getName().startsWith("b!match ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith("b!match") || message.getContentRaw().startsWith("b!mat")) &&
                                (message.getContentRaw().contains("s") || message.getContentRaw().contains("simple") || message.getContentRaw().contains("d") || message.getContentRaw().contains("double") || message.getContentRaw().contains("m") || message.getContentRaw().contains("mixte"))) {
                            if (!message.getReactions().contains(MCB.MCBEmote)) {
                                MCB.remindersManager.addReminder("BadmintonBot Match", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1h"), false);
                                message.addReaction(MCB.MCBEmote).queue();
                                break;
                            }
                        }
                    }
                }

            }else{

                if (e.getMessage().getContentRaw().startsWith("Ton sponsor ta payé ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith("b!sponsor") || message.getContentRaw().startsWith("b!spon")) {
                            if (!message.getReactions().contains(MCB.MCBEmote)) {
                                MCB.remindersManager.addReminder("BadmintonBot Sponsor", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                                message.addReaction(MCB.MCBEmote).queue();
                                break;
                            }
                        }
                    }
                }else if (e.getMessage().getContentRaw().startsWith("Vous avez récupérer vos plumes, vous avez déplumé ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith("b!breeding") || message.getContentRaw().startsWith("b!bree")) &&
                                (message.getContentRaw().contains("claim") || message.getContentRaw().contains("clai") || message.getContentRaw().contains("cla") ||message.getContentRaw().contains("cl") || message.getContentRaw().contains("c"))) {
                            if (!message.getReactions().contains(MCB.MCBEmote)) {
                                MCB.remindersManager.addReminder("BadmintonBot Breeding", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d12h"), false);
                                message.addReaction(MCB.MCBEmote).queue();
                                break;
                            }
                        }
                    }
                }

            }

        }

    }

}
