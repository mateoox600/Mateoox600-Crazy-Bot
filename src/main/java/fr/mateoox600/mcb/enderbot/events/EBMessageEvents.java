package fr.mateoox600.mcb.enderbot.events;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class EBMessageEvents extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        // check message author is EnderBot
        if (e.getAuthor().getId().equals("280726849842053120")) {

            // check if there is an embed in the message
            if (e.getMessage().getEmbeds().size() > 0) {

                // check the first field of the embed to see if this is a daily
                if (e.getMessage().getEmbeds().get(0).getFields().get(0).getName().contains("Daily récupéré") || e.getMessage().getEmbeds().get(0).getFields().get(0).getName().contains("Recovered Daily")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">da") || message.getContentRaw().startsWith(">day") || message.getContentRaw().startsWith(">daily")) {
                            MCB.remindersManager.addReminder("EnderBot Daily", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }

                // check the first field of the embed to see if this is a port claim
                } else if (e.getMessage().getEmbeds().get(0).getFields().get(0).getName().contains("Fishing")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">port") || message.getContentRaw().startsWith(">por")) &&
                                (message.getContentRaw().endsWith("claim") || message.getContentRaw().endsWith("c"))) {
                            MCB.remindersManager.addReminder("EnderBot Port", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }
                }
            } else {

                // check the message if it's a hourly
                if (e.getMessage().getContentRaw().startsWith("Vous avez récupéré : ") || e.getMessage().getContentRaw().startsWith("You found ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">hr") || message.getContentRaw().startsWith(">hour") || message.getContentRaw().startsWith(">hourly")) {
                            MCB.remindersManager.addReminder("EnderBot Hr", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1h"), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }

                // check if the message is a generator start
                } else if (e.getMessage().getContentRaw().endsWith("Votre générateur vient de démarrer. Revenez dans 4h, mais ne soyez pas en retard ;)") || e.getMessage().getContentRaw().startsWith("Your generator has been started. Come back in 4 hours, but don't be too late. ;)")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">g") || message.getContentRaw().startsWith(">gen") || message.getContentRaw().startsWith(">generator")) &&
                                (message.getContentRaw().endsWith("s") || message.getContentRaw().endsWith("start"))) {
                            MCB.remindersManager.addReminder("EnderBot Gen", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("4h"), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }

                // check if the message is a drill claim
                } else if (e.getMessage().getContentRaw().contains("Félicitations ! Votre foreuse vient de vous rapporter") || e.getMessage().getContentRaw().contains("Congratulations ! Your drill has just brought you")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">drill") || message.getContentRaw().startsWith(">dri") || message.getContentRaw().startsWith(">dril")) &&
                                (message.getContentRaw().endsWith("claim") || message.getContentRaw().endsWith("c"))) {
                            MCB.remindersManager.addReminder("EnderBot Drill", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }
                }

                // check if the message is a fishing
                else if (e.getMessage().getContentRaw().contains(" - Vous avez attrapé ") || e.getMessage().getContentRaw().contains(" - You caught ")){
                    String time = "";
                    for (String words : e.getMessage().getContentRaw().split(" ")){
                        if (words.contains(")")) {
                            try {
                                long i = RemindersManager.parseReminderTime(words.substring(0, words.length()-2));
                                if (i > 0) {
                                    time = words;
                                    break;
                                }
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">fish") || message.getContentRaw().startsWith(">fi")) {
                            MCB.remindersManager.addReminder("EnderBot Fish", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime(time), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }
                }

                // check if the message is a cau start message
                else if (e.getMessage().getContentRaw().startsWith("Vous avez commencé la création de ") || e.getMessage().getContentRaw().startsWith("You started the creation of ")){
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">cau") || message.getContentRaw().startsWith(">cauldron")) {
                            MCB.remindersManager.addReminder("EnderBot Cauldron", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime(e.getMessage().getContentRaw().split(" ")[e.getMessage().getContentRaw().split(" ").length-1]), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }

                // check if the message is a generator break message
                }else if (e.getMessage().getContentRaw().contains(" Ouch ! Il semblerait que vous soyez arrivé trop tard ... Vous venez de casser votre générateur (RIP) et n'avez rien récupéré, votre générateur doit maintenant se réparer et sera de nouveau disponible dans 24h...") || e.getMessage().getContentRaw().contains(" Ouch ! It looks like you came too late ... You just broke your generator (RIP) and you have not gained anything, your generator must now be repaired and will be available again in 24 hours...")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">g") || message.getContentRaw().startsWith(">gen") || message.getContentRaw().startsWith(">generator")) {
                            MCB.remindersManager.addReminder("EnderBot Generator Repair", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }

                // check if the message is a village claim message
                }else if (e.getMessage().getContentRaw().startsWith("Vous venez de récupérer ") || e.getMessage().getContentRaw().startsWith("You just claimed ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">vi") || message.getContentRaw().startsWith(">village")) &&
                                (message.getContentRaw().endsWith("claim") || message.getContentRaw().endsWith("c"))) {
                            MCB.remindersManager.addReminder("EnderBot Village", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onMessageUpdate(@Nonnull MessageUpdateEvent e) {

        // check message author is EnderBot
        if (e.getAuthor().getId().equals("280726849842053120")) {

            // check the edited message is a factory produce start
            if (e.getMessage().getContentRaw().contains(" - La production d'un composant de type ") || e.getMessage().getContentRaw().contains(" - Started production of one component of type ")) {
                MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                for (Message message : messageHistory.getRetrievedHistory()) {
                    if ((message.getContentRaw().startsWith(">fac") || message.getContentRaw().startsWith(">factory")) &&
                            (message.getContentRaw().contains("p") || message.getContentRaw().contains("produce"))) {
                        String time = e.getMessage().getContentRaw().split("\\*\\*")[1].substring(0, e.getMessage().getContentRaw().split("\\*\\*")[1].length()-8) + "m";
                        MCB.remindersManager.addReminder("EnderBot Factory", message.getMember(), e.getTextChannel(), RemindersManager.parseReminderTime(time), false);
                        message.addReaction(e.getGuild().getEmotesByName("MCB", false).get(0)).queue();
                        break;
                    }
                }
            }
        }
    }
}
