package fr.mateoox600.mcb.enderbot.events;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class EBMessageEvents extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        if (e.getAuthor().getId().equals("280726849842053120")){
            if (e.getMessage().getContentRaw().startsWith("Vous avez récupéré : ") || e.getMessage().getContentRaw().startsWith("You found ")){
                MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                for(Message message : messageHistory.getRetrievedHistory()){
                    if (message.getContentRaw().startsWith(">hr") || message.getContentRaw().startsWith(">hour") || message.getContentRaw().startsWith(">hourly")){
                        MCB.remindersManager.addReminder("EnderBot Hr", e.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1h"), false);
                        message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                        break;
                    }
                }
            }else if (e.getMessage().getContentRaw().endsWith("Votre générateur vient de démarrer. Revenez dans 4h, mais ne soyez pas en retard ;)") || e.getMessage().getContentRaw().startsWith("Your generator has been started. Come back in 4 hours, but don't be too late. ;)")){
                MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                for(Message message : messageHistory.getRetrievedHistory()){
                    if ((message.getContentRaw().startsWith(">g") || message.getContentRaw().startsWith(">gen") || message.getContentRaw().startsWith(">generator")) &&
                            (message.getContentRaw().endsWith("s") || message.getContentRaw().endsWith("start"))){
                        MCB.remindersManager.addReminder("EnderBot Gen", e.getMember(), e.getChannel(), RemindersManager.parseReminderTime("4h"), false);
                        message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                        break;
                    }
                }
            }
        }
    }
}
