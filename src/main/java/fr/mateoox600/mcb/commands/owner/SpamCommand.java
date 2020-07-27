package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class SpamCommand extends Command {

    public SpamCommand() {
        this.name = "s";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
            if (args.length > 1) {
                String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                    e.getChannel().sendMessage(i + ": " + message).queue();
                }
            }
        }
    }

}
