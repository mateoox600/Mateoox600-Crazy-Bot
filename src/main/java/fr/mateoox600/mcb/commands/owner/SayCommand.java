package fr.mateoox600.mcb.commands.owner;

import fr.mateoox600.mcb.MCB;
import fr.mateoox600.mcb.commands.manager.Command;
import fr.mateoox600.mcb.commands.manager.CommandEvent;

import java.util.Arrays;

public class SayCommand extends Command {

    public SayCommand() {
        this.name = "say";
        this.help = "Make the bot say things";
        this.owner = true;
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getMessage().getContentRaw().split("\\s+");
        args = Arrays.copyOfRange(args, 1, args.length);
        e.getChannel().sendMessage("```" + String.join(" ", args) + "```").queue(msg -> e.getMessage().delete().queue());
    }
}
