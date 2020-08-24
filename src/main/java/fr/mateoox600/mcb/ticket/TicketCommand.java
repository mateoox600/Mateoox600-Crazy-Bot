package fr.mateoox600.mcb.ticket;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;

public class TicketCommand extends Command {

    public TicketCommand() {
        this.name = "ticket";
        this.help = "Ticket Command to do ticket (only work on main guild)";
        this.guildOnly = "713826539698913320";
        this.aliases = new String[]{"tick", "ti"};
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length < 1) {
            if (MCB.ticketManager.getMemberTicket(e.getMember()) == null)
                MCB.ticketManager.create(e.getMember());
            else e.getChannel().sendMessage("```Error: You already have a ticket```").queue();
        } else {
            if (args[0].equalsIgnoreCase("delete")) {
                if (e.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                    Member ticketOwner = MCB.ticketManager.getMemberByChannel(e.getTextChannel());
                    if (ticketOwner == null) return;
                    MCB.ticketManager.delete(ticketOwner);
                }
            }
        }
    }
}
