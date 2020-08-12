package fr.mateoox600.mcb.bots.enderbot.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class EBOwnerCommand extends Command {

    public EBOwnerCommand() {
        this.name = "ebo";
        this.help = "Bot Owner Command: EnderBot management";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        if (e.getAuthor().getId().equals(MCB.config.getOwnerId())) {
            if (args.length > 0) {
                e.getChannel().sendMessage("<@!280726849842053120> API " + String.join(" ", args)).queue();
            }else{
                e.getChannel().sendMessage("```Error: Wrong arguments```").queue();
            }
        }
    }

}
