package fr.mateoox600.mcb.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.mateoox600.mcb.MCB;

import java.util.Arrays;

public class StopCommand extends Command {

    public StopCommand() {
        this.name = "stop";
        this.help = "Bot Owner Command: Stop the bot";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (args.length > 0) {
            if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
                e.getJDA().shutdownNow();
                System.exit(0);
            }
        }
    }

}
