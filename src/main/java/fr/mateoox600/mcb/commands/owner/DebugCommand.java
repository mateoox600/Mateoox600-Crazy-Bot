package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class DebugCommand extends Command {

    public DebugCommand() {
        this.name = "debug";
        this.help = "Bot Owner Command: Debug in dev commands";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
            e.getChannel().sendMessage("```" + e.getJDA().getGuilds() + "```").queue();
        }
    }

}
