package fr.mateoox600.mcb.ticket;

import net.dv8tion.jda.api.entities.TextChannel;

public class Ticket {

    public final TextChannel ticketChannel;

    public Ticket(TextChannel ticketChannel) {
        this.ticketChannel = ticketChannel;
    }

    public void delete() {
        ticketChannel.delete().queue();
    }

}
