package fr.mateoox600.mcb.ticket;

import fr.mateoox600.mcb.MCB;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TicketManager {

    private final HashMap<String, Ticket> tickets = new HashMap<>();
    private final HashMap<Guild, Category> category;

    public TicketManager() {
        category = new HashMap<>();
        category.put(MCB.jda.getGuildById("713826539698913320"), MCB.jda.getCategoryById("744446482089771008"));
        load();
    }
 
    public void delete(Member ticketOwner) {
        tickets.get(ticketOwner.getId()).delete();
        tickets.remove(ticketOwner.getId());
    }

    public void create(Member ticketOwner) {
        TextChannel ticketChannel = category.get(ticketOwner.getGuild()).createTextChannel(ticketOwner.getEffectiveName() + "-Ticket-" + ticketOwner.getId()).complete();
        try {
            ticketChannel.getManager().getChannel().createPermissionOverride(ticketChannel.getGuild().getPublicRole()).setDeny(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_READ).complete();
            ticketChannel.getManager().getChannel().createPermissionOverride(ticketOwner).setAllow(Permission.VIEW_CHANNEL, Permission.MESSAGE_READ, Permission.MESSAGE_WRITE).complete();
        }catch (Exception ignored){
        }
        tickets.put(ticketOwner.getId(), new Ticket(ticketChannel));
        ticketChannel.sendMessage(ticketOwner.getAsMention() + " You have created a ticket. please write your issue").queue();
    }

    public Ticket getMemberTicket(Member ticketOwner) {
        return tickets.get(ticketOwner.getId());
    }

    public Member getMemberByChannel(TextChannel ticketChannel){
        for (Map.Entry<String, Ticket> ticket : tickets.entrySet())
            if (ticket.getValue().ticketChannel.getId().equals(ticketChannel.getId()))
                return ticket.getValue().ticketChannel.getGuild().getMemberById(ticket.getKey());
        return null;
    }

    public void load() {
        for (Map.Entry<Guild, Category> categoryEntry : category.entrySet()) {
            for (TextChannel channels : categoryEntry.getValue().getTextChannels()) {
                tickets.put(Objects.requireNonNull(channels.getGuild().getMemberById(channels.getName().split("-")[2])).getId(), new Ticket(channels));
            }
        }
    }

}
