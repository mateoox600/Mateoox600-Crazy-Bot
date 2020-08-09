package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class DebugCommand extends Command {

    public DebugCommand() {
        this.name = "debug";
        this.help = "Debug in dev commands";
        this.owner = true;
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
            e.getChannel().sendMessage("```" + e.getJDA().getGuildById("713826539698913320").getChannels() + "```").queue();
    }

}
