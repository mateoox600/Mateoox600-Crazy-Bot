package fr.mateoox600.mcb.enderbot.events;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.utils.reminders.RemindersManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class EBMessageEvents extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {

        // check message author is EnderBot
        if (e.getAuthor().getId().equals("280726849842053120")) {

            // check if there is an embed in the message
            if (e.getMessage().getEmbeds().size() > 0) {

                // check the first field of the embed to see if this is a daily
                if (e.getMessage().getEmbeds().get(0).getFields().get(0).getName().contains("Daily r�cup�r�") || e.getMessage().getEmbeds().get(0).getFields().get(0).getName().contains("Recovered Daily")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">da") || message.getContentRaw().startsWith(">day") || message.getContentRaw().startsWith(">daily")) {
                            MCB.remindersManager.addReminder("EnderBot Daily", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(MCB.MCBEmote).queue();
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
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }
                }
            } else {

                /*String time = "";
                for (String words : e.getMessage().getContentRaw().split(" ")) {
                    if (words.contains(")")) {
                        try {
                            long i = RemindersManager.parseReminderTime(words.substring(0, words.length() - 2));
                            if (i > 0) {
                                time = words;
                                break;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }

                if (checkMessage(e.getMessage(), new String[]{"Vous avez r�cup�r� : ", "You found "}, "1h", "EnderBot Hr", new String[]{">hr", ">hour", ">hourly"}, new String[]{})) return;
                else if (checkMessage(e.getMessage(), new String[]{"Votre g�n�rateur vient de d�marrer. Revenez dans 4h, mais ne soyez pas en retard ;)", "Your generator has been started. Come back in 4 hours, but don't be too late. ;)"}, "4h", "EnderBot Generator", new String[]{">g", ">gen", ">generator"}, new String[]{"s", "start"})) return;
                else if (checkMessage(e.getMessage(), new String[]{"F�licitations ! Votre foreuse vient de vous rapporter", "Congratulations ! Your drill has just brought you"}, "1d", "EnderBot Drill", new String[]{">drill", ">dri", ">drill"}, new String[]{"c", "claim"})) return;
                else if (checkMessage(e.getMessage(), new String[]{" - Vous avez attrap� ", " - You caught "}, time, "EnderBot Fishing", new String[]{">fi", ">fish"}, new String[]{})) return;
                else if (checkMessage(e.getMessage(), new String[]{"Vous avez commenc� la cr�ation de ", "You started the creation of "}, e.getMessage().getContentRaw().split(" ")[e.getMessage().getContentRaw().split(" ").length - 1], "EnderBot Cauldron", new String[]{">cau", ">cauldron"}, new String[]{})) return;
                else if (checkMessage(e.getMessage(), new String[]{" Ouch ! Il semblerait que vous soyez arriv� trop tard ... Vous venez de casser votre g�n�rateur (RIP) et n'avez rien r�cup�r�, votre g�n�rateur doit maintenant se r�parer et sera de nouveau disponible dans 24h...", " Ouch ! It looks like you came too late ... You just broke your generator (RIP) and you have not gained anything, your generator must now be repaired and will be available again in 24 hours... "}, "1d", "EnderBot Generator Repair", new String[]{">g", ">gen", "generator"}, new String[]{})) return;
                else if (checkMessage(e.getMessage(), new String[]{"Vous venez de r�cup�rer ", "You just claimed "}, "1d", "EnderBot Village", new String[]{">v", ">village", "generator"}, new String[]{"claim", "c"})) return;
                else if (checkMessage(e.getMessage(), new String[]{" - Votre point de r�putation a �t� donn� avec succ�s � ", " - Your reputation point has been successfully given to "}, "1d", "EnderBot Reputation", new String[]{">rep", ">reputation"}, new String[]{e.getMessage().getContentRaw().split(" ")[e.getMessage().getContentRaw().split(" ").length - 2]})) return;
*/
                // check the message if it's a hourly
                if (e.getMessage().getContentRaw().startsWith("Vous avez r�cup�r� : ") || e.getMessage().getContentRaw().startsWith("You found ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">hr") || message.getContentRaw().startsWith(">hour") || message.getContentRaw().startsWith(">hourly")) {
                            MCB.remindersManager.addReminder("EnderBot Hr", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1h"), false);
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }

                    // check if the message is a generator start
                } else if (e.getMessage().getContentRaw().endsWith("Votre g�n�rateur vient de d�marrer. Revenez dans 4h, mais ne soyez pas en retard ;)") || e.getMessage().getContentRaw().startsWith("Your generator has been started. Come back in 4 hours, but don't be too late. ;)")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">g") || message.getContentRaw().startsWith(">gen") || message.getContentRaw().startsWith(">generator")) &&
                                (message.getContentRaw().endsWith("s") || message.getContentRaw().endsWith("start"))) {
                            MCB.remindersManager.addReminder("EnderBot Gen", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("4h"), false);
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }

                    // check if the message is a drill claim
                } else if (e.getMessage().getContentRaw().contains("F�licitations ! Votre foreuse vient de vous rapporter") || e.getMessage().getContentRaw().contains("Congratulations ! Your drill has just brought you")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">drill") || message.getContentRaw().startsWith(">dri") || message.getContentRaw().startsWith(">dril")) &&
                                (message.getContentRaw().endsWith("claim") || message.getContentRaw().endsWith("c"))) {
                            MCB.remindersManager.addReminder("EnderBot Drill", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }
                }

                // check if the message is a fishing
                else if (e.getMessage().getContentRaw().contains(" - Vous avez attrap� ") || e.getMessage().getContentRaw().contains(" - You caught ")) {
                    String time = "";
                    for (String words : e.getMessage().getContentRaw().split(" ")) {
                        if (words.contains(")")) {
                            try {
                                long i = RemindersManager.parseReminderTime(words.substring(0, words.length() - 2));
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
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }
                }

                // check if the message is a cau start message
                else if (e.getMessage().getContentRaw().startsWith("Vous avez commenc� la cr�ation de ") || e.getMessage().getContentRaw().startsWith("You started the creation of ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">cau") || message.getContentRaw().startsWith(">cauldron")) {
                            MCB.remindersManager.addReminder("EnderBot Cauldron", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime(e.getMessage().getContentRaw().split(" ")[e.getMessage().getContentRaw().split(" ").length - 1]), false);
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }

                    // check if the message is a generator break message
                } else if (e.getMessage().getContentRaw().contains(" Ouch ! Il semblerait que vous soyez arriv� trop tard ... Vous venez de casser votre g�n�rateur (RIP) et n'avez rien r�cup�r�, votre g�n�rateur doit maintenant se r�parer et sera de nouveau disponible dans 24h...") || e.getMessage().getContentRaw().contains(" Ouch ! It looks like you came too late ... You just broke your generator (RIP) and you have not gained anything, your generator must now be repaired and will be available again in 24 hours...")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if (message.getContentRaw().startsWith(">g") || message.getContentRaw().startsWith(">gen") || message.getContentRaw().startsWith(">generator")) {
                            MCB.remindersManager.addReminder("EnderBot Generator Repair", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }

                    // check if the message is a village claim message
                } else if (e.getMessage().getContentRaw().startsWith("Vous venez de r�cup�rer ") || e.getMessage().getContentRaw().startsWith("You just claimed ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">vi") || message.getContentRaw().startsWith(">village")) &&
                                (message.getContentRaw().endsWith("claim") || message.getContentRaw().endsWith("c"))) {
                            MCB.remindersManager.addReminder("EnderBot Village", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(MCB.MCBEmote).queue();
                            break;
                        }
                    }

                    // check if the message is rep giving message
                } else if (e.getMessage().getContentRaw().contains(" - Votre point de r�putation a �t� donn� avec succ�s � ") || e.getMessage().getContentRaw().startsWith(" - Your reputation point has been successfully given to ")) {
                    MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                    for (Message message : messageHistory.getRetrievedHistory()) {
                        if ((message.getContentRaw().startsWith(">rep") || message.getContentRaw().startsWith(">reputation")) &&
                                message.getContentRaw().contains(e.getMessage().getContentRaw().split(" ")[e.getMessage().getContentRaw().split(" ").length - 2])) {
                            MCB.remindersManager.addReminder("EnderBot Reputation", message.getMember(), e.getChannel(), RemindersManager.parseReminderTime("1d"), false);
                            message.addReaction(MCB.MCBEmote).queue();
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
            /*int time = Integer.parseInt(e.getMessage().getContentRaw().split("\\*\\*")[1].substring(0, e.getMessage().getContentRaw().split("\\*\\*")[1].length() - 8)) + 1;
            checkMessage(e.getMessage(), new String[]{" - La production d'un composant de type ", " - Started production of one component of type "}, time + "m", "EnderBot Factory", new String[]{">fac", ">factory"}, new String[]{"p", "produce"});*/
            if (e.getMessage().getContentRaw().contains(" - La production d'un composant de type ") || e.getMessage().getContentRaw().contains(" - Started production of one component of type ")) {
                MessageHistory messageHistory = e.getChannel().getHistoryBefore(e.getMessage(), 10).complete();
                for (Message message : messageHistory.getRetrievedHistory()) {
                    if ((message.getContentRaw().startsWith(">fac") || message.getContentRaw().startsWith(">factory")) &&
                            (message.getContentRaw().contains("p") || message.getContentRaw().contains("produce"))) {
                        String time = e.getMessage().getContentRaw().split("\\*\\*")[1].substring(0, e.getMessage().getContentRaw().split("\\*\\*")[1].length() - 8) + "m";
                        MCB.remindersManager.addReminder("EnderBot Factory", message.getMember(), e.getTextChannel(), RemindersManager.parseReminderTime(time) + RemindersManager.parseReminderTime("1m"), false);
                        message.addReaction(MCB.MCBEmote).queue();
                        break;
                    }
                }
            }
        }
    }

    public boolean checkMessage(Message message, String[] contains, String time, String name, String[] prefix, String[] suffix){
        if (stringContainsItemFromList(message.getContentRaw(), contains)) {
            MessageHistory messageHistory = message.getChannel().getHistoryBefore(message, 10).complete();
            for (Message Hmessage : messageHistory.getRetrievedHistory()) {
                if (stringContainsItemFromList(Hmessage.getContentRaw(), prefix) &&
                        stringContainsItemFromList(Hmessage.getContentRaw(), suffix)) {
                    MCB.remindersManager.addReminder(name, Hmessage.getMember(), message.getTextChannel(), RemindersManager.parseReminderTime(time), false);
                    Hmessage.addReaction(MCB.MCBEmote).queue();
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

}
